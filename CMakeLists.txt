project( Car )
find_package( OpenCV REQUIRED )
add_executable( Car Car )
target_link_libraries( Car ${OpenCV_LIBS} )
