import cv2 as cv

imag = cv.imread("./greek.jpg")

for ro, row in enumerate(imag):
    for co, pix in enumerate(row):
        b, g, r = pix
        lum = (0.2126*r) + (0.7152*g) + (0.0722*b)
        if lum < 127:
            pix = [0, 0, 0]
        else:
            pix = [255, 255, 255]
        imag[ro][co] = pix


cv.imwrite("./greek-out.jpg", imag)
