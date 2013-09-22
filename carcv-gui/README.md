# CarCV - Car recognizing and speed calculating platform

Copyright (C) 2012-2013, Ondrej Skopek
All rights reserved.
All licencing issues are addressed in file LICENCE.

Setup for eclipse project:

1. git clone into a folder somewhere
2. In Git Repository Exploring view, add all local repositories
3. File > Import > Projects from Git > Local > select the repository you cloned into > Import as general project
4. Right click on the project > Configure > Enable Maven nature
5. Maven > Update project
6. Configure > Convert to faceted project
7. In facets, enable Java 1.7, JavaScript, Dynamic Web Module 3.1
8. Hack away!

Old Setup for eclipse project:

1. Run: mvn eclipse:eclipse

3. In eclipse, select: File > Import > Existing projects into workspace > path/to/carcv_gui > select project: carcv_gui

4. Enjoy!



To build, run: mvn clean package (optional: -DskipTests)

To clean, run: mvn clean