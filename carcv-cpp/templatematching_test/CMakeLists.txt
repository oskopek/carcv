#boost libs
set(Boost_USE_STATIC_LIBS OFF)
set(Boost_USE_MULTITHREADED ON)
set(Boost_USE_STATIC_RUNTIME OFF)
find_package(Boost 1.45.0 COMPONENTS system filesystem)

if(Boost_FOUND)

	#
	include_directories( ${CARCV_SOURCE_DIR} ${Boost_INCLUDE_DIRS} )
	link_directories( ${CARCV_BINARY_DIR} )

	#Add executable tmpl, with sources...
	add_executable( tmpl templatem.cpp )

	#Link with libraries OpenCV
	# all OpenCV libs
	# boost: filesystem, system
	target_link_libraries( tmpl ${OpenCV_LIBS} ${Boost_LIBRARIES} )

endif()

# add the install targets
install( TARGETS tmpl DESTINATION bin )
#install( FILES templatem.hpp DESTINATION include )