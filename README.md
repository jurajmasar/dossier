Multilevel Marketing Manager
============================

> Disclaimer: This is a high school project created for my CS class at International Baccalaureate Diploma programme. It is not maintained anymore and contains many anti-patterns. Use it at your own risk.

Multilevel Marketing Manager is an application for managing human resources at a multilevel marketing organization called "Network World Alliance". It calculates earnings of one's employees based on amount of points they gained in the system in a given time period and displays various graphs and trends based on this data.

This project has been created on 22 February 2011 at IB Diploma at Gymnazium Jura Hronca, Bratislava, Slovakia.

It has been initially developed using BlueJ 3.0.4, and later reorganized using IntelliJ IDEA 13 CE.

Directory structure
-------------------

    README.md - this readme file
    build.xml - ANT build file that compiles and bundles this project into jars, as well as generates javadoc
    data/ - user data storage
    doc/ - javadoc documentation
    dossier.iml - IntelliJ IDEA project file
    dossier_documentation.pdf - project documentation based on the criteria for Dossier at IB Diploma
    dossier_log/ - blog about the development (in Slovak)
    java/ - source files
    libs/ - dependencies
    out/ - bundled jars
    resources/ - resources required by the application (images/...)

Usage instructions
------------------

1. checkout the repository
2. make sure you have JDK 1.7 or later installed
3. run `ant` in the project directory

Default application password is empty. If the `data/` directory is missing, program offers to regenerate the basic file structure.

License
-------

    Released under MIT license.

    Permission is hereby granted, free of charge, to any person obtaining
    a copy of this software and associated documentation files (the
    "Software"), to deal in the Software without restriction, including
    without limitation the rights to use, copy, modify, merge, publish,
    distribute, sublicense, and/or sell copies of the Software, and to
    permit persons to whom the Software is furnished to do so, subject to
    the following conditions:

    The above copyright notice and this permission notice shall be
    included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
    LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
    OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
    WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
