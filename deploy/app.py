import os
import tensorflow as tf
import cv2
import numpy as np
from flask import Flask, request, render_template, jsonify

# Initialize the Flask application
app = Flask(__name__)

# Load the pre-trained model
model = tf.keras.models.load_model('skincam_model.h5')

# Define test directories
test_image_dir = './test/images'
test_annotations_dir = './test/labelTxt'

# List of classes
classes = ['acne scar', 'blackheads', 'darkspot', 'whiteheads']
target_size = (224, 224)

# Function to parse YOLO annotations
def parse_yolo_annotation(annotation_file):
    annotations = []
    if not os.path.exists(annotation_file):
        return annotations
    with open(annotation_file, 'r') as f:
        for line in f:
            parts = line.strip().split()
            if len(parts) < 5:
                annotations.append((0, 0.0, 0.0, 0.0, 0.0))
                continue
            try:
                class_id = int(float(parts[0]))
                if class_id >= len(classes):
                    annotations.append((0, 0.0, 0.0, 0.0, 0.0))
                    continue
                x_center = float(parts[1])
                y_center = float(parts[2])
                width = float(parts[3])
                height = float(parts[4])
                annotations.append((class_id, x_center, y_center, width, height))
            except ValueError:
                annotations.append((0, 0.0, 0.0, 0.0, 0.0))
    return annotations

def calculate_severity_level(detections):
    class_detections = {}
    for detection in detections:
        class_id = detection[0]
        if class_id not in class_detections:
            class_detections[class_id] = 1
        else:
            class_detections[class_id] += 1
    if 3 in class_detections:
        del class_detections[3]
    num_classes_detected = len(class_detections)
    total_bboxes = sum(class_detections.values())
    if num_classes_detected == 0:
        return 0
    elif num_classes_detected == 1 and total_bboxes <= 3:
        return 1
    elif num_classes_detected == 2 and total_bboxes <= 4:
        return 2
    elif num_classes_detected == 3 and total_bboxes >= 5:
        return 3
    else:
        return 3

def get_level_description(level):
    descriptions = {
        0: "Level 0 (Kulit wajah sehat)",
        1: "Level 1 (Kulit wajah mengalami kerusakan ringan)",
        2: "Level 2 (Kulit wajah mengalami kerusakan sedang)",
        3: "Level 3 (Kulit wajah mengalami kerusakan parah)"
    }
    return descriptions.get(level, "Level tidak valid")

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return jsonify({"error": "No file part"})
    file = request.files['file']
    if file.filename == '':
        return jsonify({"error": "No selected file"})
    if file:
        image_path = os.path.join(test_image_dir, file.filename)
        file.save(image_path)

        # Get the corresponding annotation file
        annotation_path = os.path.join(test_annotations_dir, file.filename.replace('.jpg', '.txt'))

        # Process detections
        annotations = parse_yolo_annotation(annotation_path)
        level = calculate_severity_level(annotations)
        description = get_level_description(level)

        # Return the result
        return jsonify({
            "level": level,
            "description": description
        })

if __name__ == '__main__':
    app.run(debug=True)