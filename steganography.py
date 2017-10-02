import numpy
import cv2
import random

def get_binary_list(string):
	binary_list = []
	for character in string:
		ascii_value = ord(character)
		binary_value = bin(ascii_value)
		binary_list.append(binary_value)
	return binary_list

def print_image_array(image_array):
	print("Shape of the image : " + str(image_array.shape))
	for row in image_array:
		for col in row:
			bgr_array = col
			rgb_array = list( reversed(bgr_array) )
			print( str(rgb_array) + " " ),
		print("\n------------")

secret_message = "sak@234"
binary_list = get_binary_list(secret_message)

# cv2.imread(image) gives you BGR, instead of RGB values
image_array = cv2.imread("kaki.jpg")
print_image_array(image_array)

#x = random.randint(1, 5)
#print(x)