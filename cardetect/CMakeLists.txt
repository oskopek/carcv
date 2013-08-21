
#boost libs
set(Boost_USE_STATIC_LIBS OFF)
set(Boost_USE_MULTITHREADED ON)
set(Boost_USE_STATIC_RUNTIME OFF)
find_package(Boost 1.45.0 COMPONENTS system filesystem)

if(Boost_FOUND)

	#
	include_directories(${CARCV_SOURCE_DIR} ${Boost_INCLUDE_DIRS})
	link_directories(${CARCV_BINARY_DIR})

	#Add executable carcv, with sources...
	add_executable(carcv carimg.hpp carimg.cpp tools.hpp tools.cpp fileio.hpp fileio.cpp match.cpp match.hpp detection.hpp detection.cpp carcv.hpp carcv.cpp main.cpp)

	#Link with libraries OpenCV (Excluded: /lib/libm.so)
	# all OpenCV libs
	# boost: filesystem, system
	target_link_libraries( carcv ${OpenCV_LIBS} ${Boost_LIBRARIES})

endif()

# add the install targets
install (TARGETS carcv DESTINATION bin)
install (FILES carimg.hpp tools.hpp fileio.hpp match.hpp detection.hpp  carcv.hpp DESTINATION include)
