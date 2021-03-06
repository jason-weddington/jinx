package net.jeremybrooks.jinx.response.places;

import com.google.gson.Gson;
import net.jeremybrooks.jinx.response.activity.ActivityResponseTest;
import net.jeremybrooks.jinx.response.photos.Tag;
import net.jeremybrooks.jinx.response.tags.Tags;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jeremy Brooks
 */
public class PlaceTest {

  private static Properties polylines;

  /**
   * Load the polylines.properties file.
   * The properties file holds the (very very very) long polylines values that are expected to be
   * in the classes created from the JSON documents. They are in a separate file for the sake of
   * convenience.
   *
   * @throws Exception if the properties file cannot be loaded.
   */
  @BeforeClass
  public static void beforeClass () throws Exception {
    polylines = new Properties();
    InputStream in = ActivityResponseTest.class.getResourceAsStream("/response/places/polylines.properties");
    polylines.load(in);
    in.close();
  }

  @Test
  public void testGetInfo() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_get_info.json"));
    PlaceInfo placeInfo = new Gson().fromJson(reader, PlaceInfo.class);
    reader.close();

    assertNotNull(placeInfo);
    assertEquals("ok", placeInfo.getStat());

    Place place = placeInfo.getPlace();
    assertNotNull(place);

    assertEquals("NsbUWfBTUb4mbyVu", place.getPlaceId());
    assertEquals("2347563", place.getWoeId());
    assertEquals(new Float(37.271), place.getLatitude());
    assertEquals(new Float(-119.270), place.getLongitude());
    assertEquals("/United+States/California", place.getPlaceUrl());
    assertEquals("region", place.getPlaceType());
    assertEquals(new Integer(8), place.getPlaceTypeId());
    assertEquals("America/Los_Angeles", place.getTimezone());
    assertEquals("California, US, United States", place.getName());
    assertEquals("California", place.getWoeName());
    assertTrue(place.isHasShapeData());

    Place.Region region = place.getRegion();
    assertNotNull(region);
    assertEquals("California, US, United States", region.getContent());
    assertEquals("NsbUWfBTUb4mbyVu", region.getPlaceId());
    assertEquals("2347563", region.getWoeId());
    assertEquals(new Float(37.271), region.getLatitude());
    assertEquals(new Float(-119.270), region.getLongitude());
    assertEquals("/United+States/California", region.getPlaceUrl());

    Place.Country country = place.getCountry();
    assertNotNull(country);
    assertEquals("United States", country.getContent());
    assertEquals("nz.gsghTUb4c2WAecA", country.getPlaceId());
    assertEquals("23424977", country.getWoeId());
    assertEquals(new Float(37.167), country.getLatitude());
    assertEquals(new Float(-95.845), country.getLongitude());
    assertEquals("/United+States", country.getPlaceUrl());


    Shape shapeData = place.getShape();
    assertNotNull(shapeData);
    assertEquals(new Long(1292570642), shapeData.getCreated());
    assertEquals(new Float(0.1220703125), shapeData.getAlpha());
    assertEquals(new Integer(2156271), shapeData.getCountPoints());
    assertEquals(new Integer(178), shapeData.getCountEdges());

    assertNotNull(shapeData.getPolylines());
    assertEquals(1, shapeData.getPolylines().size());
    assertEquals(polylines.getProperty("test.getInfo"), shapeData.getPolylines().get(0).getContent());
    assertFalse(shapeData.isHasDonuthole());
    assertFalse(shapeData.isDonuthole());
    assertEquals("http://farm6.static.flickr.com/5081/shapefiles/2347563_20101217_98f440022d.tar.gz", shapeData.getShapefileUrl());
  }

  @Test
  public void testGetInfoByUrl() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_get_info_by_url.json"));
    PlaceInfo placeInfo = new Gson().fromJson(reader, PlaceInfo.class);
    reader.close();

    assertNotNull(placeInfo);
    assertEquals("ok", placeInfo.getStat());

    Place place = placeInfo.getPlace();
    assertNotNull(place);

    assertEquals("nY7EoSRSV7kW", place.getPlaceId());
    assertEquals("3534", place.getWoeId());
    assertEquals(new Float(45.536), place.getLatitude());
    assertEquals(new Float(-73.625), place.getLongitude());
    assertEquals("/Canada/Quebec/Montr%C3%A9al", place.getPlaceUrl());
    assertEquals("locality", place.getPlaceType());
    assertEquals(new Integer(7), place.getPlaceTypeId());
    assertEquals("America/Montreal", place.getTimezone());
    assertEquals( "Montreal, Quebec, Canada", place.getName());
    assertEquals("Montreal", place.getWoeName());

    Place.Locality locality = place.getLocality();
    assertNotNull(locality);
    assertEquals("Montreal, Quebec, Canada", locality.getContent());
    assertEquals("nY7EoSRSV7kW", locality.getPlaceId());
    assertEquals("3534", locality.getWoeId());
    assertEquals(new Float(45.536), locality.getLatitude());
    assertEquals(new Float(-73.625), locality.getLongitude());
    assertEquals("/Canada/Quebec/Montr%C3%A9al", locality.getPlaceUrl());

    Place.County county = place.getCounty();
    assertNotNull(county);
    assertEquals("Montreal, Quebec, Canada", county.getContent());
    assertEquals("D8x2nD5TW7mCt77vJw", county.getPlaceId());
    assertEquals("29375198", county.getWoeId());
    assertEquals(new Float(45.553), county.getLatitude());
    assertEquals(new Float(-73.600), county.getLongitude());
    assertEquals("/D8x2nD5TW7mCt77vJw", county.getPlaceUrl());

    Place.Region region = place.getRegion();
    assertNotNull(region);
    assertEquals("Quebec, Canada", region.getContent());
    assertEquals("dSpA1ldTUb7syPFr", region.getPlaceId());
    assertEquals("2344924", region.getWoeId());
    assertEquals(new Float(53.891), region.getLatitude());
    assertEquals(new Float(-68.431), region.getLongitude());
    assertEquals("/Canada/Quebec", region.getPlaceUrl());

    Place.Country country = place.getCountry();
    assertNotNull(country);
    assertEquals("Canada", country.getContent());
    assertEquals("b9iFoOpTUb6KZ3MPGQ", country.getPlaceId());
    assertEquals("23424775", country.getWoeId());
    assertEquals(new Float(56.954), country.getLatitude());
    assertEquals(new Float(-98.308), country.getLongitude());
    assertEquals("/Canada", country.getPlaceUrl());

    assertTrue(place.isHasShapeData());

    Shape shape = place.getShape();
    assertNotNull(shape);
    assertEquals(new Long(1292565208), shape.getCreated());
    assertEquals(new Float(0.00823974609375), shape.getAlpha());
    assertEquals(new Integer(82688), shape.getCountPoints());
    assertEquals(new Integer(46), shape.getCountEdges());
    assertEquals(polylines.getProperty("test.getInfoByUrl"), shape.getPolylines().get(0).getContent());
    assertTrue(shape.isHasDonuthole());
    assertFalse(shape.isDonuthole());
    assertEquals(shape.getShapefileUrl(), "http://farm6.static.flickr.com/5008/shapefiles/3534_20101217_1869920287.tar.gz");
  }

  @Test
  public void testFind() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_find.json"));
    Places places = new Gson().fromJson(reader, Places.class);
    reader.close();
    assertNotNull(places);
    assertNotNull(places.getPlaces());
    assertEquals("Idaho", places.getQuery());
    assertEquals(new Integer(6), places.getTotal());
    assertEquals(new Integer(6), (Integer)places.getPlaces().size());
    Place place = places.getPlaces().get(0);
    assertEquals("Ka1owT5TUb5H6wqX", place.getPlaceId());
    assertEquals("2347571", place.getWoeId());
    assertEquals(new Float(45.494), place.getLatitude());
    assertEquals(new Float(-114.143), place.getLongitude());
    assertEquals("/United+States/Idaho", place.getPlaceUrl());
    assertEquals("region", place.getPlaceType());
    assertEquals(new Integer(8), place.getPlaceTypeId());
    assertEquals("Idaho, United States", place.getContent());
    assertEquals("Idaho", place.getWoeName());
  }

  @Test
  public void testFindLatLon() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_find_lat_lon.json"));
    Places places = new Gson().fromJson(reader, Places.class);
    reader.close();
    assertNotNull(places);
    assertNotNull(places.getPlaces());
    assertEquals(new Integer(1), places.getTotal());
    assertEquals(new Integer(16), places.getAccuracy());
    assertEquals(new Float(37.76513627957266), places.getLatitude());
    assertEquals(new Float(-122.42020770907402), places.getLongitude());
    Place place = places.getPlaces().get(0);
    assertEquals("HMGdMeJTUb_Sf_5E2g", place.getPlaceId());
    assertEquals("23512048", place.getWoeId());
    assertEquals(new Float(37.764), place.getLatitude());
    assertEquals(new Float(-122.425), place.getLongitude());
    assertEquals("/United+States/California/San+Francisco/Mission+Dolores", place.getPlaceUrl());
    assertEquals("neighbourhood", place.getPlaceType());
    assertEquals(new Integer(22), place.getPlaceTypeId());
    assertEquals("America/Los_Angeles", place.getTimezone());
    assertEquals("Mission Dolores, San Francisco, CA, US, United States", place.getName());
    assertEquals("Mission Dolores", place.getWoeName());
  }

  @Test
  public void testGetChildrenWithPhotosPublic() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_get_children_with_photos_public.json"));
    Places places = new Gson().fromJson(reader, Places.class);
    reader.close();
    assertNotNull(places);
    assertNotNull(places.getPlaces());
    assertEquals(new Integer(99), places.getTotal());
    Place place = places.getPlaces().get(0);
    assertEquals( "GddgqTpTUb8LgT93hw", place.getPlaceId());
    assertEquals( "23512022", place.getWoeId());
    assertEquals(new Float(37.792), place.getLatitude());
    assertEquals(new Float(-122.400), place.getLongitude());
    assertEquals("/United+States/California/San+Francisco/Financial+District", place.getPlaceUrl());
    assertEquals("neighbourhood", place.getPlaceType());
    assertEquals(new Integer(22), place.getPlaceTypeId());
    assertEquals("America/Los_Angeles", place.getTimezone());
    assertEquals("Financial District, San Francisco, CA, US, United States", place.getContent());
    assertEquals("Financial District", place.getWoeName());
    assertEquals(new Integer(130215), place.getPhotoCount());
  }

  @Test
  public void testGetPlaceTypes() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_get_place_types.json"));
    PlaceTypes placeTypes = new Gson().fromJson(reader, PlaceTypes.class);
    assertNotNull(placeTypes);
    List<PlaceTypes.PlaceType> list = placeTypes.getPlaceTypes();
    assertNotNull(list);
    assertEquals(6, list.size());
    PlaceTypes.PlaceType type =list.get(3);
    assertEquals(new Integer(8), type.getPlaceTypeId());
    assertEquals("region", type.getTypeName());
  }

  @Test
  public void testGetShapeHistory() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_get_shape_history.json"));
    ShapeHistory shapeHistory = new Gson().fromJson(reader, ShapeHistory.class);
    assertNotNull(shapeHistory);
    assertEquals(new Integer(6), shapeHistory.getTotal());
    assertEquals("23512048", shapeHistory.getWoeId());
    assertEquals("HMGdMeJTUb_Sf_5E2g", shapeHistory.getPlaceId());
    assertEquals("neighbourhood", shapeHistory.getPlaceType());
    assertEquals(new Integer(22), shapeHistory.getPlaceTypeId());
    List<Shape> shapeList = shapeHistory.getShapeList();
    assertNotNull(shapeList);
    Shape shape = shapeList.get(0);
    assertEquals(new Long(1292367866), shape.getCreated());
    assertEquals(new Float(0.00001), shape.getAlpha());
    assertEquals(new Integer(6762), shape.getCountPoints());
    assertEquals(new Integer(37), shape.getCountEdges());
    assertEquals(polylines.getProperty("test.getShapeHistory"), shape.getPolylines().get(0).getContent());
    assertFalse(shape.isDonuthole());
    assertEquals("http://farm6.static.flickr.com/5241/shapefiles/23512048_20101214_4480f6f8e1.tar.gz", shape.getShapefileUrl());
  }

  @Test
  public void testGetTopPlacesList() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_get_top_places_list.json"));
    Places places = new Gson().fromJson(reader, Places.class);
    assertNotNull(places);
    assertEquals(new Integer(100), places.getTotal());
    assertEquals(new Long(1484524800), places.getDateStart());
    assertEquals(new Long(1484611199), places.getDateStop());
    Place place = places.getPlaces().get(0);
    assertEquals("2eIY2QFTVr_DwWZNLg", place.getPlaceId());
    assertEquals("24554868", place.getWoeId());
    assertEquals(new Float(52.883), place.getLatitude());
    assertEquals(new Float(-1.974), place.getLongitude());
    assertEquals("/United+Kingdom/England", place.getPlaceUrl());
    assertEquals("region", place.getPlaceType());
    assertEquals(new Integer(8), place.getPlaceTypeId());
    assertEquals("Europe/London", place.getTimezone());
    assertEquals("England, GB, United Kingdom", place.getContent());
    assertEquals("England", place.getWoeName());
    assertEquals(new Integer(3406), place.getPhotoCount());
  }

  @Test
  public void testGetPlacesForBoundingBox() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_get_places_for_bounding_box.json"));
    Places places = new Gson().fromJson(reader, Places.class);
    assertNotNull(places);
    assertEquals("region", places.getPlaceType());
    assertEquals(new Integer(1), places.getTotal());
    assertEquals(new Integer(1), places.getPages());
    assertEquals(new Integer(1), places.getPage());
    assertEquals("-122.42307100000001,37.773779,-122.381071,37.815779", places.getBoundingBox());
    Place place = places.getPlaces().get(0);
    assertEquals( "NsbUWfBTUb4mbyVu", place.getPlaceId());
    assertEquals( "2347563", place.getWoeId());
    assertEquals(new Float(37.271), place.getLatitude());
    assertEquals(new Float(-119.270), place.getLongitude());
    assertEquals("/United+States/California", place.getPlaceUrl());
    assertEquals("region", place.getPlaceType());
    assertEquals(new Integer(8), place.getPlaceTypeId());
    assertEquals( "America/Los_Angeles", place.getTimezone());
    assertEquals("California, US, United States", place.getContent());
    assertEquals("California", place.getWoeName());
  }

  @Test
  public void testPlacesForContacts() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_places_for_contacts.json"));
    Places places = new Gson().fromJson(reader, Places.class);
    assertNotNull(places);
    assertEquals(new Integer(51), places.getTotal());
    Place place = places.getPlaces().get(0);
    assertEquals("NsbUWfBTUb4mbyVu", place.getPlaceId());
    assertEquals( "2347563", place.getWoeId());
    assertEquals(new Float(37.271), place.getLatitude());
    assertEquals(new Float(-119.270), place.getLongitude());
    assertEquals("/United+States/California", place.getPlaceUrl());
    assertEquals("region", place.getPlaceType());
    assertEquals(new Integer(8), place.getPlaceTypeId());
    assertEquals("America/Los_Angeles", place.getTimezone());
    assertEquals("California, US, United States", place.getContent());
    assertEquals("California", place.getWoeName());
    assertEquals(new Integer(306211), place.getPhotoCount());
  }

  @Test
  public void testPlacesForTags() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_places_for_tags.json"));
    Places places = new Gson().fromJson(reader, Places.class);
    assertNotNull(places);
    assertEquals(new Integer(75), places.getTotal());
    Place place = places.getPlaces().get(0);
    assertEquals("byMt1GpTWrhtqOpahg", place.getPlaceId());
    assertEquals( "28288823", place.getWoeId());
    assertEquals(new Float(40.760), place.getLatitude());
    assertEquals(new Float(-73.983), place.getLongitude());
    assertEquals("/United+States/New+York/New+York/Theater+District", place.getPlaceUrl());
    assertEquals("neighbourhood", place.getPlaceType());
    assertEquals(new Integer(22), place.getPlaceTypeId());
    assertEquals("America/New_York", place.getTimezone());
    assertEquals("Theater District, New York, NY, US, United States", place.getContent());
    assertEquals("Theater District", place.getWoeName());
    assertEquals(new Integer(2670), place.getPhotoCount());
  }

  @Test
  public void testPlacesForUser() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_places_for_user.json"));
    Places places = new Gson().fromJson(reader, Places.class);
    assertNotNull(places);
    assertEquals(new Integer(70), places.getTotal());
    Place place = places.getPlaces().get(0);
    assertEquals("KomQdIRUV7IFfyO5Xw", place.getPlaceId());
    assertEquals( "55861471", place.getWoeId());
    assertEquals(new Float(37.909), place.getLatitude());
    assertEquals(new Float(-122.311), place.getLongitude());
    assertEquals("/United+States/California/Richmond/Richmond+Annex", place.getPlaceUrl());
    assertEquals("neighbourhood", place.getPlaceType());
    assertEquals(new Integer(22), place.getPlaceTypeId());
    assertEquals("America/Los_Angeles", place.getTimezone());
    assertEquals("Richmond Annex, Richmond, CA, US, United States", place.getContent());
    assertEquals("Richmond Annex", place.getWoeName());
    assertEquals(new Integer(66), place.getPhotoCount());
  }

  @Test
  public void testTagsForPlace() throws Exception {
    InputStreamReader reader = new InputStreamReader(ActivityResponseTest.class.getResourceAsStream("/response/places/sample_tags_for_place.json"));
    Tags tags = new Gson().fromJson(reader, Tags.class);
    assertNotNull(tags);
    assertEquals(new Integer(100), tags.getTotal());
    Tag tag = tags.getTagList().get(0);
    assertEquals("portrait", tag.getTag());
    assertEquals(new Integer(8809), tag.getCount());
  }
}
