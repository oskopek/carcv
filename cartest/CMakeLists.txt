include_directories(${CARCV_SOURCE_DIR}/)
link_directories(${CARCV_BINARY_DIR}/)

#
add_executable(Car2 matcher_simple.cpp)
target_link_libraries( Car2 ${OpenCV_LIBS} )
