import java.awt.GridBagLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
public class InnerGridBagLayout2 extends JPanel {
	public InnerGridBagLayout2() {
		super();
		super.setLayout(new GridBagLayout());
	}
	private List<ComponentGridBagConstraints> components = new ArrayList<ComponentGridBagConstraints>();
	public void add(Component component, GridBagConstraints gbc) {
		ComponentGridBagConstraints compgbc= new ComponentGridBagConstraints();
		compgbc.component = component;
		compgbc.gbc = gbc;
		components.add(compgbc);
		super.add(component,gbc);
		Sort();
	}
	public void Sort() {
		List<ComponentGridBagConstraints> components2 = clone();
		LiveIterator<ComponentGridBagConstraints> liveiterator = new LiveIterator<ComponentGridBagConstraints>(components2);
		while(liveiterator.hasNext()) {
			ComponentGridBagConstraints componentgbc= liveiterator.next();
			liveiterator.remove(componentgbc);
			for(int i = 0; i < components2.size(); i++) {
				ComponentGridBagConstraints componentgbc2= components2.get(i);
				if(componentgbc.gbc.gridx == componentgbc2.gbc.gridx) {
					liveiterator.remove(componentgbc2);
					i--;
					if(! Compare(liveiterator,componentgbc,componentgbc2,components2) ) { 
						Compare(liveiterator,componentgbc2,componentgbc,components2);
					}
				}
			}
		}
	}
	public boolean Compare(LiveIterator<ComponentGridBagConstraints> liveiterator,ComponentGridBagConstraints componentgbc,ComponentGridBagConstraints componentgbc2,List<ComponentGridBagConstraints> components2) {
		boolean didcompare = false;
		if(componentgbc.gbc.gridwidth < componentgbc2.gbc.gridwidth) {
			didcompare = true;
			int x = 0;
			super.remove(componentgbc.component);
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			componentgbc.gbc.gridx = 0;
			panel.add(componentgbc.component,componentgbc.gbc);
			x+=componentgbc.gbc.gridwidth;
			for(int j = 0; j < components2.size(); j++) {	
				ComponentGridBagConstraints componentgbc3 = components2.get(j);
				if(componentgbc3.gbc.gridy == componentgbc.gbc.gridy
				 && componentgbc3.gbc.gridx > componentgbc.gbc.gridx &&
				 componentgbc3.gbc.gridx < (componentgbc2.gbc.gridx+componentgbc2.gbc.gridwidth)
				) {
					liveiterator.remove(componentgbc3);
					super.remove(componentgbc3.component);
					j--;
					componentgbc3.gbc.gridx = x;
					panel.add(componentgbc3.component,componentgbc3.gbc);
					x+= componentgbc3.gbc.gridwidth;
				}
			}
			super.add(panel);		
		}
		return didcompare;
	}
	/*
	** This removes all JComponents from GridBagLayout
	*/
	public void Remove() {
		for(int i = 0; i < components.size(); i++) {
			super.remove(components.get(i).component);
		}
	}
	public List<ComponentGridBagConstraints> clone() {
		List<ComponentGridBagConstraints> components2= new ArrayList<ComponentGridBagConstraints>();
		for(int i =0; i < components.size(); i++) {
			components2.add(components.get(i));
		}
		return components2;
	}
}


		