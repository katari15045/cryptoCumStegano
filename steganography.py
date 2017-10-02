import numpy
import cv2
import random

# (row, col, color) ---- row -> row of an image array, col -> col of an image array, color -> one among R, G, B

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

def get_location_for_secret_message(secret_binary_list, image_array):
	location_array = []
	for binary_string in secret_binary_list:
		current_index = 2
		max_index = len(binary_string) - 1
		while( current_index <= max_index ):
			#format of location_array = [row,col,color, color_binary_bit_index] for example [234,165,1, 3]
			current_location = get_location_for_a_secret_bit(binary_string, image_array, current_index)
			location_array.append(current_location)
			current_index = current_index + 1
	location_string = str(location_array)
	return location_string

def get_image_array_bit_index(image_array, row, col, color_index, target_bit):
	target_bit = int(target_bit)
	color_value = image_array[row][col][color_index]
	binary_color_value = bin(color_value)
	current_index = 2
	max_index = len(binary_color_value) - 1
	found_status = False

	while( current_index <= max_index ):
		current_bit = int( binary_color_value[current_index] )
		if( current_bit == target_bit ):
			found_status = True
			return (found_status, current_index)
		current_index = current_index + 1
	return (found_status, current_index)

def get_location_for_a_secret_bit(binary_string, image_array, binary_string_index):
	(rows, cols, colors) = image_array.shape
	secret_bit = binary_string[binary_string_index]

	while(True):
		random_row = random.randint(0, rows-1)
		random_col = random.randint(0, cols-1)
		random_color = random.randint(0, colors-1)
		(found_status, color_binary_bit_index) = get_image_array_bit_index(image_array, random_row, random_col, random_color, secret_bit)
		if( found_status ):
			break
	location_array = [ random_row, random_col, random_color, color_binary_bit_index ]
	return location_array

secret_message = "sak@234"
secret_binary_list = get_binary_list(secret_message)

# cv2.imread(image) gives you BGR, instead of RGB values
image_array = cv2.imread("kaki.jpg")
secret_message_location = get_location_for_secret_message(secret_binary_list, image_array)
print( "location of secret message : \n" +  secret_message_location)
