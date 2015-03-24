# WKB

#### Well-Known Binary ####

WKB is a Java library for writing and reading Well-Known Binary Geometries to and from bytes. The library includes a hierarchy of Geometry objects.

This WKB library was developed at the National Geospatial-Intelligence Agency (NGA) in collaboration with [BIT Systems](https://www.bit-sys.com/index.jsp). The government has "unlimited rights" and is releasing this software to increase the impact of government investments by providing developers with the opportunity to take things in new directions. The software use, modification, and distribution rights are stipulated within the [MIT license](http://choosealicense.com/licenses/mit/).

### Pull Requests ###
If you'd like to contribute to this project, please make a pull request. We'll review the pull request and discuss the changes. All pull request contributions to this project will be released under the MIT license.

Software source code previously released under an open source license and then modified by NGA staff is considered a "joint work" (see 17 USC § 101); it is partially copyrighted, partially public domain, and as a whole is protected by the copyrights of the non-government authors and must be released according to the terms of the original open source license.

### Usage ###

#### Read ####

    //byte[] bytes = ...    
    
    ByteReader reader = new ByteReader(bytes);
    reader.setByteOrder(ByteOrder.BIG_ENDIAN);
    Geometry geometry = WkbGeometryReader.readGeometry(reader);
    GeometryType geometryType = geometry.getGeometryType();

#### Write ####

    //Geometry geometry = ...
    
    ByteWriter writer = new ByteWriter();
    writer.setByteOrder(ByteOrder.BIG_ENDIAN);
    WkbGeometryWriter.writeGeometry(writer, geometry);
    byte[] bytes = writer.getBytes();
    writer.close();

### License ###

    The MIT License (MIT)

    Copyright (c) 2015 BIT Systems

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.


