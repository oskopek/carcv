#Minimal CMake version required
cmake_minimum_required(VERSION 2.8)

#Name of the project
project( CARCV )

#Required lib
find_package( OpenCV REQUIRED )

#Debug flag:
IF(CMAKE_COMPILER_IS_GNUCC)
	SET(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -g")
ENDIF(CMAKE_COMPILER_IS_GNUCC)
IF(CMAKE_COMPILER_IS_GNUCXX)
	SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g")
ENDIF(CMAKE_COMPILER_IS_GNUCXX)


#List of subdirectories to build
add_subdirectory(src)
add_subdirectory(templatematching_test)
