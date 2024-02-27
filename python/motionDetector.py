import cv2

video_capture = cv2.VideoCapture(0)

# Read the first frame and convert it to grayscale
ret, frame = video_capture.read()
prev_frame = cv2.cvtColor(cv2.resize(
    frame, (0, 0), fx=0.25, fy=0.25), cv2.COLOR_BGR2GRAY)
prev_frame = cv2.GaussianBlur(prev_frame, (21, 21), 0)

while True:
    # Capture frame-by-frame and preprocess
    ret, frame = video_capture.read()
    gray = cv2.cvtColor(cv2.resize(frame, (0, 0), fx=0.25,
                        fy=0.25), cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (21, 21), 0)

    # Compute the absolute difference between the current frame and previous frame
    frame_delta = cv2.absdiff(prev_frame, gray)
    thresh = cv2.threshold(frame_delta, 25, 255, cv2.THRESH_BINARY)[1]

    # Dilate the thresholded image to fill in holes, then find contours on thresholded image
    thresh = cv2.dilate(thresh, None, iterations=2)
    contours, _ = cv2.findContours(
        thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    motion_detected = len(contours) > 0

    if motion_detected:
        # yes motion
        frame = cv2.putText(frame, 'Motion', (20, 20),
                            cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255),
                            2, cv2.LINE_AA)

    # Display the resulting frame
    cv2.imshow('Video', frame)

    prev_frame = gray  # Update the previous frame

    # Hit 'q' on the keyboard to quit!
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# When everything is done, release the capture
video_capture.release()
cv2.destroyAllWindows()
