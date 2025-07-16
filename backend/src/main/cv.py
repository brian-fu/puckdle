import mediapipe as mp
import cv2
import numpy as np
import requests

video_path = 'video.mp4'
output_path = 'blurred_output.mp4'

mp_pose = mp.solutions.pose
pose = mp_pose.Pose(static_image_mode=False)
mp_drawing = mp.solutions.drawing_utils
cap = cv2.VideoCapture(video_path)

# Video properties
frame_width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
frame_height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
fps = cap.get(cv2.CAP_PROP_FPS)

# Saves video
four_char_code = cv2.VideoWriter_fourcc(*'mp4v')
out = cv2.VideoWriter(output_path, four_char_code, fps, (frame_width, frame_height))

while cap.isOpened():
    ret, frame = cap.read()
    if not ret:
        break

    h, w, _ = frame.shape
    rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    results = pose.process(rgb)

    if results.pose_landmarks:
        # Get landmark coordinates
        landmarks = results.pose_landmarks.landmark
        x_coords = [int(lm.x * w) for lm in landmarks]
        y_coords = [int(lm.y * h) for lm in landmarks]

        # Define bounding box around person
        x_min = max(min(x_coords) - 20, 0)
        x_max = min(max(x_coords) + 20, w)
        y_min = max(min(y_coords) - 20, 0)
        y_max = min(max(y_coords) + 20, h)

        # Blur the region
        roi = frame[y_min:y_max, x_min:x_max]
        if roi.size != 0:
            blurred_roi = cv2.GaussianBlur(roi, (99, 99), 30)
            frame[y_min:y_max, x_min:x_max] = blurred_roi

    # Write frame to output video
    out.write(frame)

cap.release()
out.release()