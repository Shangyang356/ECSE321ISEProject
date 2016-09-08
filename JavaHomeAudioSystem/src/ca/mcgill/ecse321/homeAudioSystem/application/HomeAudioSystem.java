package ca.mcgill.ecse321.homeAudioSystem.application;

import ca.mcgill.ecse321.homeAudioSystem.persistence.PersistenceHAS;
import ca.mcgill.ecse321.homeAudioSystem.view.HASPage1;

/**
 * Starts the aplication GUI after loading any saved data
 */
public class HomeAudioSystem
{
	public static void main(String[] args)
	{
		PersistenceHAS.loadHASModel();

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				new HASPage1().setVisible(true);
			}
		});
	}
}