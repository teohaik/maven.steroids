package gr.teohaik;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class PomParser {

	private static List<File> pomFiles = new ArrayList<>();
	public static List<Dependency> DEPENDENCIES = new ArrayList<>();


	public static void parse(File givenFile) {
		handleFile(givenFile);

		for(File pomFile: pomFiles) {
			if (pomFile == null)
				return;
			parsePomFile(pomFile);
		}
	}

	private static void parsePomFile(File pomFile) {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model = null;
		try {
			model = reader.read(new FileReader(pomFile));

			List<Dependency> dependencies = model.getDependencies();

			dependencies.forEach(dep->
			{
				String version = dep.getVersion();
				dep.setVersion("6.6.6");
			});

			MavenXpp3Writer writer = new MavenXpp3Writer();
			writer.write(new FileOutputStream(pomFile),model);

			DEPENDENCIES.addAll(dependencies);

		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}

	}

	private static File handleFile(File givenFile) {
		if (givenFile.isDirectory()) {
			collectPomFiles(givenFile.listFiles());
		}
		if(givenFile.getName().equals("pom.xml")){
			pomFiles.add(givenFile);
		}
        if(givenFile.getName().equals("test-pom.xml")){
            pomFiles.add(givenFile);
        }
		return null;
	}


	private static void collectPomFiles(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				collectPomFiles(file.listFiles()); // Calls same method again.
			} else {
				if (file.getName().equalsIgnoreCase("pom.xml")) {
					pomFiles.add(file);
				}
			}
		}
	}


}
