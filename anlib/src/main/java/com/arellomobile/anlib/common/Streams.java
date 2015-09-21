package com.arellomobile.anlib.common;

import static com.arellomobile.anlib.common.Checks.requireNotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Sep 21, 2013
 * 
 * @author denis.mirochnik
 */
public class Streams
{
	private static final int BUFFER = 8192;

	//TODO byte[] pool
	//TODO maybe stream to file?
	//TODO maybe strem from file?
	//TODO maybe channels?

	public static void transfer(InputStream from, OutputStream to) throws IOException
	{
		from = new BufferedInputStream(requireNotNull(from), BUFFER);
		to = new BufferedOutputStream(requireNotNull(to), BUFFER);

		final byte[] buf = new byte[BUFFER];
		int read;

		while ((read = from.read(buf)) != -1)
		{
			to.write(buf, 0, read);
		}

		to.flush();
	}

	public static byte[] streamToBytes(InputStream stream) throws IOException
	{
		requireNotNull(stream);

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		transfer(stream, baos);

		return baos.toByteArray();
	}

	public static String streamToString(InputStream stream) throws IOException
	{
		return streamToString(stream, "UTF-8");
	}

	public static String streamToString(InputStream stream, String charset) throws IOException
	{
		final InputStreamReader reader = new InputStreamReader(requireNotNull(stream), charset);
		final StringWriter writer = new StringWriter();

		transfer(reader, writer);

		return writer.toString();
	}

	public static void transfer(Reader from, Writer to) throws IOException
	{
		from = new BufferedReader(requireNotNull(from), BUFFER);
		to = new BufferedWriter(requireNotNull(to), BUFFER);

		final char[] buf = new char[BUFFER];
		int read;

		while ((read = from.read(buf)) != -1)
		{
			to.write(buf, 0, read);
		}

		to.flush();
	}

	public static char[] readerToChars(Reader reader) throws IOException
	{
		requireNotNull(reader);

		final CharArrayWriter baos = new CharArrayWriter();

		transfer(reader, baos);

		return baos.toCharArray();
	}

	public static String readerToString(Reader reader) throws IOException
	{
		requireNotNull(reader);

		final StringWriter baos = new StringWriter();

		transfer(reader, baos);

		return baos.toString();
	}

	public static void drain(InputStream stream) throws IOException
	{
		stream = new BufferedInputStream(requireNotNull(stream), BUFFER);

		while (stream.read() != -1)
		{

		}
	}

	public static void drain(Reader reader) throws IOException
	{
		reader = new BufferedReader(requireNotNull(reader), BUFFER);

		while (reader.read() != -1)
		{

		}
	}

	private Streams()
	{
	}
}
