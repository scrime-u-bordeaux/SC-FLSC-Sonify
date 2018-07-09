Sonify_Formula {
	// variables d'instance
	var cal, time, dyn, mel, lmass, lharm, lcol;

	*new {|cal, time, dyn, mel, lmass, lharm, lcol|
		^super.new.formulaInit(cal, time, dyn, mel, lmass, lharm, lcol);
	}

	formulaInit {|pcal, ptime, pdyn, pmel, plmass, plharm, plcol|
		cal = pcal;
		time = ptime;
		dyn = pdyn;
		mel = pmel;
		lmass = plmass;
		// trier les fonctions de masse
		lmass.sort {|a,b| a.order < b.order};
		// vérifier l'absence de doublons
		lmass.doAdjacentPairs {|a,b|
			if (a.order == b.order)
			{ Error("Duplicate mass functions.").throw };
		};
		lharm = plharm;
		lcol = plcol;
		^this;
	}

	// retourne la chaîne FLSC associée
	asString {
		^"(sonify % % % % [%] [%] [%])".format(cal, time, dyn, mel,
			lmass.collect(_.asString).reduce('+') ? "",
			lharm.collect(_.asString).reduce('+') ? "",
			lcol.collect(_.asString).reduce('+') ? ""
		);
	}
}
			