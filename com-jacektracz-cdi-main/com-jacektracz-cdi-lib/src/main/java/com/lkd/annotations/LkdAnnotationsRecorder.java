/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lkd.annotations;

/**
 *
 * @author jacek
 */
import java.io.DataInput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@code AnnotationDetector} reads Java Class File (".class") files and reports the
 * encountered annotations via a simple, developer friendly API.
 * <p>
 * A Java Class File consists of a stream of 8-bit bytes. All 16-bit, 32-bit, and 64-bit
 * quantities are constructed by reading in two, four, and eight consecutive 8-bit
 * bytes, respectively. Multi byte data items are always stored in big-endian order,
 * where the high bytes come first. In the Java and Java 2 platforms, this format is
 * supported by interfaces {@link java.io.DataInput} and {@link java.io.DataOutput}.
 * <p>
 * A class file consists of a single ClassFile structure:
 * <pre>
 * ClassFile {
 *   u4 magic;
 *   u2 minor_version;
 *   u2 major_version;
 *   u2 constant_pool_count;
 *   cp_info constant_pool[constant_pool_count-1];
 *   u2 access_flags;
 *   u2 this_class;
 *   u2 super_class;
 *   u2 interfaces_count;
 *   u2 interfaces[interfaces_count];
 *   u2 fields_count;
 *   field_info fields[fields_count];
 *   u2 methods_count;
 *   method_info methods[methods_count];
 *   u2 attributes_count;
 *   attribute_info attributes[attributes_count];
 * }
 *
 * Where:
 * u1 unsigned byte {@link java.io.DataInput#readUnsignedByte()}
 * u2 unsigned short {@link java.io.DataInput#readUnsignedShort()}
 * u4 unsigned int {@link java.io.DataInput#readInt()}
 *
 * Annotations are stored as Attributes (i.e. "RuntimeVisibleAnnotations" and
 * "RuntimeInvisibleAnnotations").
 * </pre>
 * References:
 * <ul>
 * <li><a href="http://en.wikipedia.org/wiki/Java_class_file">Java class file (Wikipedia)</a>
 * (Gentle Introduction);
 * <li><a href="http://download.oracle.com/otndocs/jcp/jcfsu-1.0-fr-eval-oth-JSpec/">Class
 * File Format Specification</a> (Java 6 version) and the
 * <a href="http://java.sun.com/docs/books/jvms/second_edition/html/ClassFile.doc.html">Java
 * VM Specification (Chapter 4)</a> for the real work.
 * <li><a href="http://stackoverflow.com/questions/259140">scanning java annotations at
 * runtime</a>.
 * </ul>
 * <p>
 * Similar projects / libraries:
 * <ul>
 * <li><a href="http://community.jboss.org/wiki/MCScanninglib">JBoss MC Scanning lib</a>;
 * <li><a href="http://code.google.com/p/reflections/">Google Reflections</a>, in fact an
 * improved version of <a href="http://scannotation.sourceforge.net/">scannotation</a>;
 * <li><a herf="https://github.com/ngocdaothanh/annovention">annovention</a>, improved version
 * of the <a href="http://code.google.com/p/annovention">original Annovention</a> project.
 * Available from maven: {@code tv.cntt:annovention:1.2};
 * <li>If using the Spring Framework, use {@code ClassPathScanningCandidateComponentProvider}
 * </ul>
 * <p>
 * All above mentioned projects make use of a byte code manipulation library (like BCEL,
 * ASM or Javassist).
 *
 * @author <a href="mailto:rmuller@xiam.nl">Ronald K. Muller</a>
 * @since annotation-detector 3.0.0
 */
public final class LkdAnnotationsRecorder {

    
}