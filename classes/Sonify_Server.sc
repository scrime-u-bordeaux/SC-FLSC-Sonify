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
		// initialiser l'interpréteur
		// remplacer par:
		// interpreter = FLSC_Interpreter().loadPackage("chemin_du_paquet_Sonify");
		// interpreter = true;
		interpreter = FLSC_Interpreter();
	}

	job {|formula, id, filename|
		// rendu effectué de façon asynchrone: Function.fork
		{
			// TEST: attendre un temps indéterminé
			// 12.0.rand.wait;
			// évaluer la formule et effectuer le calback
			// remplacer par:
			// interpreter.read(formula.asFLSC).recordNRT(..., doneAction: doneAction)
			// interpreter.value(formula);
			interpreter.read(formula).recordNRT(filename,
				doneAction: {"%: %".format(id, filename).postln};
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
		^super.free;
	}
}