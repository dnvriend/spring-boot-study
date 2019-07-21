package com.github.dnvriend.converters;

import com.github.dnvriend.avro.Person;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * There are two data serialization formats which Avro supports: JSON format and Binary format.
 *
 * First, we’ll focus on the JSON format and then we’ll discuss the Binary format.
 *
 * Classes for serialization:
 * <ul>
 *     <li>DatumWriter (Interface): Write data of a Schema.</li>
 *     <ul>
 *         <li>SpecificDatumWriter: writes generated Java classes</li>
 *         <li>GenericDatumWriter: writes genereric Java objects</li>
 *         <li>ProtobufDatumWriter: For Writing generated protobuf classes</li>
 *         <li>ReflectDatumWriter: Writes existing classes with Java Reflection</li>
 *         <li>ThriftDatumWriter: Writing generated thrift classes</li>
 *     </ul>
 *     <li>DatumReader (Interface): Read data of a schema</li>
 *     <ul>
 *         <li>SpecificDatumReader: read generated Java classes</li>
 *         <li>GenericDatumReader: read generic Java objects</li>
 *         <li>ProtobufDatumReader: for generated protobuf classes</li>
 *         <li>ReflectDatumReader: for existing classes with Java Reflection</li>
 *         <li>ThriftDatumReader: for generated thrift classes</li>
 *     </ul>
 *     <li>Decoder (Interface): for deserializing Avro values</li>
 *     <ul>
 *         <li>BinaryDecoder: for decoding binary-format data</li>
 *         <li>JsonDecoder: for decoding JSON-data encoding</li>
 *     </ul>
 * </ul>
 */
@Converter
public class PersonAttributeConverter implements AttributeConverter<Person, byte[]> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public byte[] convertToDatabaseColumn(Person attribute) {
        DatumWriter<Person> writer = new SpecificDatumWriter<>(Person.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder;
        try {
            jsonEncoder = EncoderFactory.get().jsonEncoder(Person.getClassSchema(), stream);
            writer.write(attribute, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            logger.error("Serialization error:" + e.getMessage());
        }
        return data;
    }

    @Override
    public Person convertToEntityAttribute(byte[] dbData) {
        DatumReader<Person> reader = new SpecificDatumReader<>(Person.class);
        Decoder decoder;
        try {
            decoder = DecoderFactory.get().jsonDecoder(Person.getClassSchema(), new String(dbData));
            return reader.read(null, decoder);
        } catch (IOException e) {
            logger.error("Deserialization error:" + e.getMessage());
        }
        return null;
    }
}