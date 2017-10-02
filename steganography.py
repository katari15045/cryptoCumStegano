import numpy
import cv2
import random

# (row, col, color) ---- row -> row of an image array, col -> col of an image array, color -> one among R, G, B

def get_binary_list(string):
	binary_list = []
	for character in string:
		ascii_value = ord(character)
		# format(int, '#(no.of digits)') i.e to maintain 7+2 bits and avoiding 0b11 for 3 instead it gives 0b0000011
		binary_value = format(ascii_value, '#09b')
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
	return location_array

def get_image_array_bit_index(image_array, row, col, color_index, target_bit):
	target_bit = int(target_bit)
	color_value = image_array[row][col][color_index]
	binary_color_value = format(color_value, '#09b')
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

def decode_secret_message_location(image_array, secret_message_location):
	secret_message_binary_string = []
	pipe_splitted_str = secret_message_location.split("|")
	for element in pipe_splitted_str:
		comma_splitted_str = element.split(",")
		current_row = int(comma_splitted_str[0])
		current_col = int(comma_splitted_str[1])
		current_color_index = int(comma_splitted_str[2])
		current_color_bit_index = int(comma_splitted_str[3])
		target_color_value = image_array[current_row][current_col][current_color_index]
		target_color_binary_string = format(target_color_value, '#09b')
		target_bit = target_color_binary_string[current_color_bit_index]
		secret_message_binary_string.append(target_bit)
	return secret_message_binary_string

def encode_location_array(secret_message_location):
	encoded_string = ""
	for element in secret_message_location:
		for integer in element:
			str_integer = str(integer)
			encoded_string = encoded_string + str_integer
			encoded_string = encoded_string + ","
		# removing last character of the string
		encoded_string = encoded_string[:-1]
		encoded_string = encoded_string + "|"
	encoded_string = encoded_string[:-1]
	return encoded_string

secret_message = "sak@234"
secret_binary_list = get_binary_list(secret_message)
print("secret binary list : " + str(secret_binary_list))

# cv2.imread(image) gives you BGR, instead of RGB values
image_array = cv2.imread("kaki.jpg")
secret_message_location = get_location_for_secret_message(secret_binary_list, image_array)
#print("Size : " + str(len(secret_message_location)) )
#print( "location array : \n" +  str(secret_message_location) )
encoded_location_string = encode_location_array(secret_message_location)
print("encoded location string : \n" + encoded_location_string)

decoded_message = decode_secret_message_location(image_array, encoded_location_string)
print("Decoded message : " + str(decoded_message) )
