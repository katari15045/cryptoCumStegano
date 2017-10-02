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

secret_message = "sak@234"
binary_list = get_binary_list(secret_message)
print( str(binary_list) )

#image_array = cv2.imread("kaki.jpg")
#print("Shape of the image : " + str(image_array.shape))

#x = random.randint(1, 5)
#print(x)