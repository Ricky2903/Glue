package com.glue.feed.html;

import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaginatedListStrategy implements VisitorStrategy {

    static final Logger LOG = LoggerFactory
	    .getLogger(PaginatedListStrategy.class);

    private SiteMap siteMap;

    private VisitorListener visitorListener = new DefaultVisitorListener();

    private HTMLFetcher hf = new HTMLFetcher();

    public PaginatedListStrategy(SiteMap siteMap) {
	this.siteMap = siteMap;
    }

    @Override
    public void visit() throws Exception {
	String baseUri = siteMap.getFrontUrl();
	Document doc = hf.fetch(baseUri);

	ElementDecorator elem = new ElementDecorator(doc);

	boolean nextPage = false;
	int numPage = 0;

	do {
	    numPage++;
	    LOG.info("Page number  = " + numPage);

	    // A base URL for event details page
	    URLFilter filter = siteMap.getUrlFilter();
	    if (filter != null) {
		Set<String> linkHrefs = elem.listLinks(filter);
		for (String linkHref : linkHrefs) {
		    // Notify listener
		    visitorListener.processLink(linkHref);
		}

	    } else {
		// Elements that match the list selector
		Elements elems = doc.select(siteMap.getListSelector());

		for (Element e : elems) {
		    ElementDecorator other = new ElementDecorator(e);
		    // Link to the event details page
		    String linkHref = other.firstLink();

		    if (linkHref != null) {
			// Notify listener
			visitorListener.processLink(linkHref);
		    }
		}
	    }

	    // Pagination
	    // Elements that match the end of data condition
	    if (siteMap.getEndOfDataCondition() != null) {
		Elements lastPageElems = doc.select(siteMap
			.getEndOfDataCondition());
		if (!lastPageElems.isEmpty()) {
		    // We reached the last page
		    break;
		}
	    }

	    // Elements that match the next page selector
	    if (siteMap.getNextPageSelector() != null) {
		Elements nextPageElems = doc.select(siteMap
			.getNextPageSelector());

		// The link to the next page is the first matching element: we
		// ignore the case where there is a link for each individual
		// page, with no link to the next page
		Element nextPageElem = nextPageElems.first();
		nextPage = (nextPageElem != null);

		if (nextPage) {
		    ElementDecorator other = new ElementDecorator(nextPageElem);
		    // Load the next page
		    String linkHref = other.firstLink();
		    if (linkHref != null) {
			doc = hf.fetch(linkHref);
		    }
		}
	    }

	} while (nextPage);

    }

    public VisitorListener getVisitorListener() {
	return visitorListener;
    }

    @Override
    public void setVisitorListener(VisitorListener visitorListener) {
	this.visitorListener = visitorListener;
    }

}
