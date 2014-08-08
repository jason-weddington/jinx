/*
 * Jinx is Copyright 2010-2014 by Jeremy Brooks and Contributors
 *
 * Jinx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jinx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Jinx.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.jeremybrooks.jinx.api;

import net.jeremybrooks.jinx.Jinx;
import net.jeremybrooks.jinx.JinxConstants;
import net.jeremybrooks.jinx.OAuthAccessToken;
import net.jeremybrooks.jinx.logger.JinxLogger;
import net.jeremybrooks.jinx.logger.StdoutLogger;
import net.jeremybrooks.jinx.response.photos.upload.CheckTicketsResponse;
import net.jeremybrooks.jinx.response.photos.upload.UploadResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by jeremyb on 8/1/14.
 */
public class PhotosUploadApiTest {
    private static PhotosUploadApi photosUploadApi;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Properties p = new Properties();
        p.load(OAuthApiTest.class.getResourceAsStream("/response/auth/secret.properties"));
        String filename = p.getProperty("path.to.oauth.token");
        assertNotNull(filename);
        File file = new File(filename);
        assertTrue(file.exists());
        OAuthAccessToken oAuthAccessToken = new OAuthAccessToken();
        oAuthAccessToken.load(new FileInputStream(file));
        assertNotNull(oAuthAccessToken);
        Jinx jinx = new Jinx(p.getProperty("flickr.key"), p.getProperty("flickr.secret"), oAuthAccessToken);
        jinx.setVerboseLogging(true);
        System.setProperty(JinxConstants.JINX_LOG_MULTIPART, "true");
        JinxLogger.setLogger(new StdoutLogger());
        photosUploadApi = new PhotosUploadApi(jinx);
    }

    @Test
    public void testCheckTickets() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("124834485-72157645804301208");
        CheckTicketsResponse response = photosUploadApi.checkTickets(list);
        assertNotNull(response);
        assertEquals("ok", response.getStat());
        assertEquals(0, response.getCode());
        assertNotNull(response.getTicketList());
        assertEquals(1, response.getTicketList().size());
        CheckTicketsResponse.Ticket ticket = response.getTicketList().get(0);
        assertEquals("124834485-72157645804301208", ticket.getTicketId());
        assertEquals("1407299391", ticket.getImported());
        assertEquals(JinxConstants.TicketStatus.completed, ticket.getTicketStatus());
    }

    @Test
    public void testUploadPhoto() throws Exception {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File("/Users/jeremyb/Desktop/IMG_4757.MOV")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int readNum; (readNum = in.read(buf)) != -1;) {
            baos.write(buf, 0, readNum);
        }
        byte[] photoData = baos.toByteArray();

        String description = "Reflections on the streets of San Francisco.";
        List<String> tags = new ArrayList<String>();
        tags.add("Jinx Upload Test");

        UploadResponse response = photosUploadApi.upload(photoData, "Clear Implications", description, tags, null, null, null, null, null, null);
        assertNotNull(response);
        assertEquals("ok", response.getStat());
        assertEquals(0, response.getCode());
        assertNotNull(response.getPhotoId());
    }

    /**
     * To test uploading a file, uncomment and provide the full path to a photo.
     * @throws Exception
     */
    @Test
    public void testUploadPhotoFile() throws Exception {
//        File file = new File("/path/to/photo");
//        String description = "";
//        List<String> tags = new ArrayList<String>();
//        tags.add("Jinx Upload Test");
//
//        UploadResponse response = photosUploadApi.upload(file, null, description, tags, null, null, null, null, null, null);
//        assertNotNull(response);
//        assertEquals("ok", response.getStat());
//        assertEquals(0, response.getCode());
//        assertNotNull(response.getPhotoId());
    }

    @Test
    public void testAsyncUploadPhoto() throws Exception {
        BufferedInputStream in = new BufferedInputStream(PhotosUploadApiTest.class.getResourceAsStream("/Clear Implications.jpg"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int readNum; (readNum = in.read(buf)) != -1;) {
            baos.write(buf, 0, readNum);
        }
        byte[] photoData = baos.toByteArray();

        String description = "Reflections on the streets of San Francisco.";
        List<String> tags = new ArrayList<String>();
        tags.add("Jinx Upload Test");

        UploadResponse response = photosUploadApi.uploadAsync(photoData, "Clear Implications", description, tags, null, null, null, null, null, null);
        assertNotNull(response);
        assertEquals("ok", response.getStat());
        assertEquals(0, response.getCode());
        assertNotNull(response.getTicketId());
    }

    /**
     * To test async uploading a file, uncomment and provide the full path to a photo.
     * @throws Exception
     */
    @Test
    public void testAsyncUploadPhotoFile() throws Exception {
//        File file = new File("/path/to/photo");
//        String description = "";
//        List<String> tags = new ArrayList<String>();
//        tags.add("Jinx Upload Test");
//
//        UploadResponse response = photosUploadApi.uploadAsync(file, null, description, tags, null, null, null, null, null, null);
//        assertNotNull(response);
//        assertEquals("ok", response.getStat());
//        assertEquals(0, response.getCode());
//        assertNotNull(response.getPhotoId());
    }
}
