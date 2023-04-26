package renderEngine.sourceEngineCompatability.dataStructure.model.sourceEngineCompatability;

import renderEngine.sourceEngineCompatability.dataStructure.model.sourceEngineCompatability.fileStructure.VMFFileEntry;

import java.nio.file.Path;
import java.util.ArrayList;

public class Job {

	public VMFFileEntry file;
	public ArrayList<Path> resourcePaths = new ArrayList<Path>();
	public boolean SuppressWarnings;
	public boolean skipTools;
}