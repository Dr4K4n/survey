package com.prodyna.ted.survey.page;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * 
 * HeaderPanel
 * 
 * @author Daniel Knipping
 *
 */
public class HeaderPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public HeaderPanel(String id) {
		super(id);
		
		add(new Image("pdImage", new ImageResource("global.logo.png")));
	}

	private static class ImageResource extends PackageResourceReference {
		private static final long serialVersionUID = 1L;

		public ImageResource(String name) {
			super(ImageResource.class, name);
		}
	}

}
