Try:
----

`mvn clean compile

cp build/install/carcv test/all/

cd test/all/

./carcv --cascade="../../test_tools/t5.xml" --method=DETECTDEMO list

cd ..

../test_tools/countefficiency.sh`
