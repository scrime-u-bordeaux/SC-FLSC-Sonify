Sonify_Formula : Sonify_Element {
	// variables d'instance
	var cal, <time, dyn, mel, lmass, lharm, lcol;

	*new {|cal, time, dyn, mel, lmass, lharm, lcol|
		^super.new(nil, time.size).formulaInit(cal, time, dyn, mel, lmass, lharm, lcol);
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

	*default {
		^super.new(nil, 2).formulaDefaultInit;
	}

	formulaDefaultInit {
		^this.formulaInit(
			Sonify_Calibre.default(this),
			Sonify_Time.default(this),
			Sonify_Dyn.default(this),
			Sonify_Parm.default(this),
			[], [], []
		);
	}

	insertTime {|index|
		super.insertTime;
		([cal, dyn, mel] ++ lmass ++ lharm ++ lcol).do(_.insertTime(index));
	}

	removeTime {|index|
		super.insertTime;
		([cal, dyn, mel] ++ lmass ++ lharm ++ lcol).do(_.removeTime(index));
	}

	makeView {|window|
		view = TreeView(window);
		view.columns = ["",""];
		cal.makeView(view.addChild(["calibre",""]));
		time.makeView(view.addChild(["temps",""]));
		dyn.makeView(view.addChild(["amplitude",""]));
		mel.makeView(view.addChild(["frequence",""]));
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
			