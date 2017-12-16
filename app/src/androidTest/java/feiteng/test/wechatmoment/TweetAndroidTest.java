package feiteng.test.wechatmoment;

import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import feiteng.test.wechatmoment.items.Tweet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test some function of the class Tweet
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TweetAndroidTest {
    @Test
    public void test_json_to_tweet() throws Exception {
        JSONObject object = new JSONObject("{\n" +
                "    \"content\": \"沙发！\",\n" +
                "    \"images\": [\n" +
                "        {\n" +
                "            \"url\": \"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"url\": \"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlJRALAf-76JPOLohBKzBg8Ab4Q5pWeQhF5igSfBflE_UYbqu7\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"url\": \"http://i.ytimg.com/vi/rGWI7mjmnNk/hqdefault.jpg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"sender\": {\n" +
                "        \"username\": \"jport\",\n" +
                "        \"nick\": \"Joe Portman\",\n" +
                "        \"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w\"\n" +
                "    },\n" +
                "    \"comments\": [\n" +
                "        {\n" +
                "            \"content\": \"Good.\",\n" +
                "            \"sender\": {\n" +
                "                \"username\": \"outman\",\n" +
                "                \"nick\": \"Super hero\",\n" +
                "                \"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"content\": \"Like it too\",\n" +
                "            \"sender\": {\n" +
                "                \"username\": \"inman\",\n" +
                "                \"nick\": \"Doggy Over\",\n" +
                "                \"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}");


        Tweet tweet = new Tweet(object);

        assertNotNull(tweet);
        assertEquals(3, tweet.getImages().size());
        assertEquals("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg", tweet.getImages().get(0));
        assertEquals("jport", tweet.getSenderProfile().getUsrName());
        assertEquals(2, tweet.getComments().size());
    }

    @Test
    public void test_json_to_tweet2() throws Exception {
        JSONObject object = new JSONObject("{\n" +
                "    \"content\": \"Unlike many proprietary big data processing platform different, Spark is built on a unified abstract RDD, making it possible to deal with different ways consistent with large data processing scenarios, including MapReduce, Streaming, SQL, Machine Learning and Graph so on. To understand the Spark, you have to understand the RDD. \",\n" +
                "    \"images\": [\n" +
                "      {\n" +
                "        \"url\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS3AqhlL_Ubqa8G_usBmy3q8z0cg8JieuVb1pV2nie4vikVEP5U\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"sender\": {\n" +
                "      \"username\": \"snowman\",\n" +
                "      \"nick\": \"Coolzzz\",\n" +
                "      \"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w\"\n" +
                "    }\n" +
                "  }");


        Tweet tweet = new Tweet(object);

        assertNotNull(tweet);
        assertEquals(1, tweet.getImages().size());
        assertEquals("snowman", tweet.getSenderProfile().getUsrName());
        assertEquals(0, tweet.getComments().size());
    }

    @Test
    public void test_json_to_tweet3() throws Exception {
        JSONObject object = new JSONObject("{\n" +
                "    \"content\": \"As a programmer, we should as far as possible away from the Windows system. However, the most a professional programmer, and very difficult to bypass Windows this wretched existence but in fact very real, then how to quickly build a simple set of available windows based test environment? See Qiu Juntao's blog. \",\n" +
                "    \"images\": [\n" +
                "      {\n" +
                "        \"url\": \"https://mail.google.com/mail/u/1/?ui=2&ik=573a5ca95f&view=att&th=14878f4660e51a86&attid=0.1&disp=emb&realattid=ii_146c85172c506890&zw&atsh=1\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"sender\": {\n" +
                "      \"username\": \"cfo\",\n" +
                "      \"nick\": \"Rebecca\",\n" +
                "      \"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQ70sXmZUxrBeVb7V24ilJG2EApZ60UMPFxB5WGsSlIUxxLnyaZXw\"\n" +
                "    },\n" +
                "    \"comments\": [\n" +
                "      {\n" +
                "        \"content\": \"Good.\",\n" +
                "        \"sender\": {\n" +
                "          \"username\": \"linkman\",\n" +
                "          \"nick\": \"Who am I\",\n" +
                "          \"avatar\": \"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTaXCeM5qX-v322Fkdjnjyl6PswPnEgUOlwxBFxVgnzXP1sm4m5rA\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  ");


        Tweet tweet = new Tweet(object);

        assertNotNull(tweet);
        assertEquals(1, tweet.getImages().size());
        assertEquals("cfo", tweet.getSenderProfile().getUsrName());
        assertEquals(1, tweet.getComments().size());
        assertEquals("linkman", tweet.getComments().get(0).getSenderProfile().getUsrName());
    }


}
