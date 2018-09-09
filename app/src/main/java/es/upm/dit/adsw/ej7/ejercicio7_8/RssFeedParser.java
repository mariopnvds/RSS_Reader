package es.upm.dit.adsw.ej7.ejercicio7_8;

/*
 * Autores de la práctica:
 * Alberto Jiménez Aliste
 * Alejandro Marcos Arias
 * Mario Penavades Suárez
 * Daniel Suárez Souto
 */
/*
 * Adaptado del proyecto Android. Cambiado para el formato Atom.
 *
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses generic Atom feeds.
 * Available at http://developer.android.com/intl/es/samples/BasicSyncAdapter/src/com.example.android.basicsyncadapter/net/FeedParser.html
 * <p/>
 * <p>Given an InputStream representation of a feed, it returns a List of entries,
 * where each list element represents a single entry (post) in the XML feed.
 * <p/>
 * <p>An example of an Atom feed can be found at:
 * http://en.wikipedia.org/w/index.php?title=Atom_(standard)&oldid=560239173#Example_of_an_Atom_1.0_feed
 */
public class RssFeedParser {
    // Constants indicting XML element names that we're interested in
    private static final int TAG_ID = 1;
    private static final int TAG_TITLE = 2;
    private static final int TAG_PUBLISHED = 3;
    private static final int TAG_LINK = 4;
    private static final int TAG_DESCRIPTION = 5;
    private static final String ID = "guid";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String PUBLISHED = "pubDate";
    private static final String DESCRIPTION = "description";
    private static final String CHANNEL = "channel";
    private static final String ENTRY = "item";

    // We don't use XML namespaces
    private static final String ns = null;

    private static final String TAG = RssFeedParser.class.getName();

    /**
     * Parse an Atom feed, returning a collection of Entry objects.
     *
     * @param in Atom feed, as a stream.
     * @return List of {@link RssItem} objects.
     * @throws XmlPullParserException on error parsing feed.
     * @throws IOException            on I/O error.
     */
    public List<RssItem> parse(InputStream in)
            throws XmlPullParserException, IOException, ParseException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        // Leemos tag rss
        parser.nextTag();
        // Log.v(TAG, "Expected rss obtained tag  " + parser.getName());
        // Leemos tag channel
        parser.nextTag();
        // Log.v(TAG, "Expected channel obtained tag " + parser.getName());
        return readFeed(parser);
    }

    /**
     * Decode a feed attached to an XmlPullParser.
     *
     * @param parser Incoming XMl
     * @return List of {@link RssItem} objects.
     * @throws XmlPullParserException on error parsing feed.
     * @throws IOException            on I/O error.
     */
    private List<RssItem> readFeed(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException {
        List<RssItem> entries = new ArrayList<RssItem>();

        /*
        <rss xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:media="http://search.yahoo.com/mrss/" xmlns:atom="http://www.w3.org/2005/Atom" xmlns:nyt="http://www.nytimes.com/namespaces/rss/2.0" version="2.0">
            <channel>
                <title>NYT > Technology</title>
                <link>http://www.nytimes.com/pages/technology/index.html?partner=rss&emc=rss</link>
                <atom:link rel="self" type="application/rss+xml" href="http://www.nytimes.com/services/xml/rss/nyt/Technology.xml"/>
                <description/>
                <language>en-us</language>
                <copyright>Copyright 2016 The New York Times Company</copyright>
                <lastBuildDate>Sat, 23 Apr 2016 11:21:26 GMT</lastBuildDate>
                <image>
                    <title>NYT > Technology</title>
                    <url>https://static01.nyt.com/images/misc/NYT_logo_rss_250x40.png</url>
                    <link>http://www.nytimes.com/pages/technology/index.html?partner=rss&emc=rss</link>
                </image>
                <item></item>
             </channel>
         </rss>
        */

        parser.require(XmlPullParser.START_TAG, ns, CHANNEL);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Log.v(TAG, "Expected " + ENTRY + " "  + " obtained " + name);
            // Starts by looking for the <item> tag. This tag repeates inside of <channel> for each
            // article in the feed.
            //
            // Example:
            /*
            <item>
                <title>Apple Services Shut Down in China in Startling About-Face</title>
                <link>http://www.nytimes.com/2016/04/22/technology/apple-no-longer-immune-to-chinas-scrutiny-of-us-tech-firms.html?partner=rss&emc=rss</link>
                <guid isPermaLink="true">http://www.nytimes.com/2016/04/22/technology/apple-no-longer-immune-to-chinas-scrutiny-of-us-tech-firms.html</guid>
                <atom:link rel="standout" href="http://www.nytimes.com/2016/04/22/technology/apple-no-longer-immune-to-chinas-scrutiny-of-us-tech-firms.html?partner=rss&emc=rss"/>
                <media:content url="https://static01.nyt.com/images/2016/04/22/business/22APPLE/22APPLE-moth.jpg" medium="image" height="151" width="151"/>
                <media:description>An Apple Store in Beijing. Facing a slowdown in sales of iPhones in the United States, Apple is looking to China for growth.</media:description>
                <media:credit>Gilles Sabrie for The New York Times</media:credit>
                <description>The iBooks Store and iTunes Movies were closed six months after they began in China, which had been more welcoming of Apple than other tech companies.</description>
                <dc:creator>PAUL MOZUR and JANE PERLEZ</dc:creator>
                <pubDate>Fri, 22 Apr 2016 00:48:58 GMT</pubDate>
                <category domain="http://www.nytimes.com/namespaces/keywords/nyt_geo">China</category>
                <category domain="http://www.nytimes.com/namespaces/keywords/nyt_org_all">Apple Inc</category>
                <category domain="http://www.nytimes.com/namespaces/keywords/des">iPhone</category>
                <category domain="http://www.nytimes.com/namespaces/keywords/mdes">Industrial Espionage</category>
                <category domain="http://www.nytimes.com/namespaces/keywords/nyt_org_all">
                State Administration of Press, Publication, Radio, Film and Television (China)
                </category>
                <category domain="http://www.nytimes.com/namespaces/keywords/nyt_org_all">Alibaba Group Holding Ltd</category>
                <category domain="http://www.nytimes.com/namespaces/keywords/nyt_per">Xi Jinping</category>
            </item>
             */
            if (name.equals(ENTRY)) {
                entries.add(readEntry(parser));
            } else {
                // Log.v(TAG, "skip " + name);
                skip(parser);
            }
        }
        return entries;
    }


    /**
     * Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
     * off to their respective "read" methods for processing. Otherwise, skips the tag.
     */
    private RssItem readEntry(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException {
        parser.require(XmlPullParser.START_TAG, ns, ENTRY);
        String id = null;
        String title = null;
        String link = null;
        String publishedOn = null;
        String description = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;
            String name = parser.getName();
            switch (name) {
                case ID:
                    // Example: <id>urn:uuid:218AC159-7F68-4CC6-873F-22AE6017390D</id>
                    id = readTag(parser, TAG_ID);
                    break;
                case TITLE:
                    // Example: <title>Article title</title>
                    title = readTag(parser, TAG_TITLE);
                    break;
                case LINK:
                    // Example: <link rel="alternate" type="text/html" href="http://example.com/article/1234"/>
                    //
                    // Multiple link types can be included. readAlternateLink() will only return
                    // non-null when reading an "alternate"-type link. Ignore other responses.
                    String tempLink = readTag(parser, TAG_LINK);
                    if (tempLink != null)
                        link = tempLink;
                    break;
                case PUBLISHED:
                    // Example: <pubDate>Sat, 23 Apr 2016 00:22:46 GMT</pubDate>
                    publishedOn = readTag(parser, TAG_PUBLISHED);
                    break;
                case DESCRIPTION:
                    // Example: <media:description>An Apple Store in Beijing. F
                    description = readTag(parser, TAG_DESCRIPTION);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        Log.d(TAG, "read item " + id + " " + title + " " + description + " " + publishedOn);
        return new RssItem(id, title, description, publishedOn, link);
    }

    /**
     * Process an incoming tag and read the selected value from it.
     */
    private String readTag(XmlPullParser parser, int tagType)
            throws IOException, XmlPullParserException {

        // Log.v(TAG, "readTag "  + tagType);
        switch (tagType) {
            case TAG_ID:
                return readBasicTag(parser, ID);
            case TAG_TITLE:
                return readBasicTag(parser, TITLE);
            case TAG_PUBLISHED:
                return readBasicTag(parser, PUBLISHED);
            case TAG_LINK:
                return readBasicTag(parser, LINK);
            case TAG_DESCRIPTION:
                return readBasicTag(parser, DESCRIPTION);
            default:
                throw new IllegalArgumentException("Unknown tag type: " + tagType);
        }
    }

    /**
     * Reads the body of a basic XML tag, which is guaranteed not to contain any nested elements.
     * <p/>
     * <p>You probably want to call readTag().
     *
     * @param parser Current parser object
     * @param tag    XML element tag name to parse
     * @return Body of the specified tag
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readBasicTag(XmlPullParser parser, String tag)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    /**
     * Processes link tags in the feed.
     */
    private String readAlternateLink(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        String link = null;
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String relType = parser.getAttributeValue(null, "rel");
        if (relType.equals("alternate"))
            link = parser.getAttributeValue(null, "href");
        while (true) {
            if (parser.nextTag() == XmlPullParser.END_TAG)
                break;
            // Intentionally break; consumes any remaining sub-tags.
        }
        return link;
    }

    /**
     * For the tags title and summary, extracts their text values.
     */
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
     * if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
     * finds the matching END_TAG (as indicated by the value of "depth" being 0).
     */
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG)
            throw new IllegalStateException();
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
