package com.example.articlesviewerapp.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Article {

    @SerializedName("uri") private String uri;
    @SerializedName("url") private String url;
    @SerializedName("id") private long id;
    @SerializedName("asset_id") private long assetId;
    @SerializedName("source") private String source;
    @SerializedName("published_date") private String publishedDate;
    @SerializedName("updated") private String updated;
    @SerializedName("section") private String section;
    @SerializedName("subsection") private String subsection;
    @SerializedName("nytdsection") private String nytdSection;
    @SerializedName("adx_keywords") private String adxKeywords;
    @SerializedName("column") private String column = null;
    @SerializedName("byline") private String byline;
    @SerializedName("type") private String type;
    @SerializedName("title") private String title;
    @SerializedName("abstract") private String _abstract;
    @SerializedName("des_facet") private ArrayList<String> desFacet;
    @SerializedName("org_facet") private ArrayList<String> orgFacet;
    @SerializedName("per_facet") private ArrayList<String> perFacet;
    @SerializedName("geo_facet") private ArrayList<String> geoFacet;
    @SerializedName("media") private ArrayList<Media> media;
    @SerializedName("eta_id") private int etaId;

    public String getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }

    public long getAssetId() {
        return assetId;
    }

    public String getSource() {
        return source;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getUpdated() {
        return updated;
    }

    public String getSection() {
        return section;
    }

    public String getSubsection() {
        return subsection;
    }

    public String getNytdSection() {
        return nytdSection;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public String getColumn() {
        return column;
    }

    public String getByline() {
        return byline;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public ArrayList<String> getDesFacet() {
        return desFacet;
    }

    public ArrayList<String> getOrgFacet() {
        return orgFacet;
    }

    public ArrayList<String> getPerFacet() {
        return perFacet;
    }

    public ArrayList<String> getGeoFacet() {
        return geoFacet;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public int getEtaId() {
        return etaId;
    }

    public class Media{
        @SerializedName("type") private String type;
        @SerializedName("subtype") private String subtype;
        @SerializedName("caption") private String caption;
        @SerializedName("copyright") private String copyright;
        @SerializedName("approved_for_syndication") private String approvedForSyndication;
        @SerializedName("media-metadata") private ArrayList<MediaMetaData> mediaMetadata;

        public String getType() {
            return type;
        }

        public String getSubtype() {
            return subtype;
        }

        public String getCaption() {
            return caption;
        }

        public String getCopyright() {
            return copyright;
        }

        public String getApprovedForSyndication() {
            return approvedForSyndication;
        }

        public ArrayList<MediaMetaData> getMediaMetadata() {
            return mediaMetadata;
        }
    }

    public class MediaMetaData{
        @SerializedName("url") private String url;
        @SerializedName("format") private String format;
        @SerializedName("height") private String height;
        @SerializedName("width") private String width;

        public String getUrl() {
            return url;
        }

        public String getFormat() {
            return format;
        }

        public String getHeight() {
            return height;
        }

        public String getWidth() {
            return width;
        }
    }
}
