Sonify_Formula {
	// fonctions utilitaires
	classvar flscArray, sonifyParm;
	// variables d'instance
	var cal, time, dyn, mel, lmass, lharm, lcol;

	*initClass {
		flscArray = {|array|
			if (array.notEmpty)
			{"[%]".format(array.collect(_.asString).reduce('+'))}
			{"[]"};
		};

		sonifyParm = {|parm|
			var first = parm.first;
			var res = case
			{first.isNumber} {first.asString}
			{first.isString} {first}
			{flscArray.(first)};

			var rest = parm[1..];
			if (rest.notEmpty)
			{
				res = res + rest.collect {|item|
					sonifyParm.(item)
				}.reduce('+');

			};

			"[%]".format(res);
		};
	}

	*new {|cal, time, dyn, mel, lmass, lharm, lcol|
		^super.new.formulaInit(cal, time, dyn, mel, lmass, lharm, lcol);
	}

	formulaInit {|pcal, ptime, pdyn, pmel, plmass, plharm, plcol|
		cal = pcal;
		time = ptime;
		dyn = pdyn;
		mel = pmel;
		lmass = plmass;
		lharm = plharm;
		lcol = plcol;
		^this;
	}

	test {
		^flscArray;
	}

	// retourne la chaîne FLSC associée
	asString {
		// calibre
		^"(sonify %".format(cal) +
		// temps
		flscArray.(time) +
		// dynamique
		sonifyParm.(dyn) +
		// profil melodique
		sonifyParm.(mel) +
		// liste de fonctions de masse
		flscArray.(lmass.collect {|it| sonifyParm.(it)}) +
		// liste de fonctions de timbre
		flscArray.(lharm.collect {|it| sonifyParm.(it)}) +
		// liste de fonctions de couleur
		flscArray.(lcol.collect {|it| sonifyParm.(["col"] ++ it)}) ++
		")"
	}
}
			