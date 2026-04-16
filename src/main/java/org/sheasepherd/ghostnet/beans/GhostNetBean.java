package org.sheasepherd.ghostnet.beans;

import java.io.Serializable;
import java.util.List;

import org.sheasepherd.ghostnet.dao.GhostNetDAO;
import org.sheasepherd.ghostnet.model.GhostNet;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class GhostNetBean implements Serializable{


	@Inject
	private GhostNetDAO ghostNetDAO;
	
	private List<GhostNet> zuBergendeNetze;
	
	public List<GhostNet> getZuBergendeNetze() {
		if (zuBergendeNetze == null) {
			zuBergendeNetze = ghostNetDAO.zuBergendeNetze();
		}
		return zuBergendeNetze;
	}
	
}
