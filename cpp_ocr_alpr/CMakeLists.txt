#boost libs
set(Boost_USE_STATIC_LIBS OFF)
set(Boost_USE_MULTITHREADED ON)
set(Boost_USE_STATIC_RUNTIME OFF)
find_package(Boost 1.45.0 COMPONENTS system filesystem)

if(Boost_FOUND)

	#
	include_directories( ${CARCV_SOURCE_DIR} ${Boost_INCLUDE_DIRS} )
	link_directories( ${CARCV_BINARY_DIR} )

	#Add executables, with sources...
	add_executable( ocr_train ocr_train.cpp ocr_train.hpp )
	add_executable( ocr_recognizer ocr_recognizer.cpp ocr_recognizer.hpp )
	add_executable( surf surf.cpp)

	#Link with libraries OpenCV
	# all OpenCV libs
	# boost: filesystem, system
	target_link_libraries( ocr_train ${OpenCV_LIBS} ${Boost_LIBRARIES} )
	target_link_libraries( ocr_recognizer ${OpenCV_LIBS} ${Boost_LIBRARIES} )
	target_link_libraries( surf ${OpenCV_LIBS} ${Boost_LIBRARIES} )

endif()

# add the install targets
install( TARGETS ocr_train ocr_recognizer surf DESTINATION bin )
install( FILES ocr_recognizer.hpp ocr_train.hpp surf.hpp DESTINATION include )
