Sonify_Server {
	// le FLSC_Interpreter
	var interpreter;

	*new {
		// instanciation de base
		// appelle le constructeur de la superclasse, jusqu'à Object
		// puis effectue l'initialisation
		^super.new.serverInit();
	}

	serverInit {
		var pkgPath = Platform.userExtensionDir +/+ "SC-FLSC-Sonify" +/+ "pkgs";
		// initialiser l'interpréteur
		interpreter = FLSC_Interpreter()
		.loadPackage(pkgPath +/+ "masse.flscpkg")
		.loadPackage(pkgPath +/+ "timbre.flscpkg")
		.loadPackage(pkgPath +/+ "sonify.flscpkg");
	}

	job {|formula, filename, doneAction|
		// rendu effectué de façon asynchrone: Function.fork
		{
			// évaluer la formule avec le callback souhaité
			interpreter.read(formula.asString)
			.recordNRT(filename,
				// renvoyer un message précisant l'identifiant et le nom de fichier
				// le nom de fichier pourrait suffire
				// ou bien il pourrait être généré automatiquement
				doneAction: doneAction;
			)
		}.fork;
		// se retourne lui-même (implicitement):
		// ^this;
		// mais peut retourner toute autre valeur utile
	}

	// lorsqu'on a terminé
	free {
		// libérer l'interpréteur
		interpreter.free;
		// libérer l'Object lui-même
		^super.free;
	}
}