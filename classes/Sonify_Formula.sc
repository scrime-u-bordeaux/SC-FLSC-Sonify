Sonify_Formula : Sonify_Element {
	// variables d'instance
	var cal, time, dyn, mel, lmass, lharm, lcol;

	*new {|cal, time, dyn, mel, lmass, lharm, lcol|
		^super.new.formulaInit(cal, time, dyn, mel, lmass, lharm, lcol);
	}

	formulaInit {|pcal, ptime, pdyn, pmel, plmass, plharm, plcol|
		time = ptime.setParent(this);
		cal = pcal.setParent(this);
		dyn = pdyn.setParent(this);
		mel = pmel.setParent(this);
		lmass = plmass.setParent(this);
		lharm = plharm.setParent(this);
		lcol = plcol.setParent(this);
		^this;
	}

	*default {
		^super.new(2).formulaDefaultInit;
	}

	formulaDefaultInit {
		^this.formulaInit(
			Sonify_Calibre.default,
			Sonify_Time.default,
			Sonify_Dyn.default,
			Sonify_Parm.default,
			Sonify_LFunc(\mass),
			Sonify_LFunc(\harm),
			Sonify_LFunc(\col)
		);
	}

	insertTime {|index|
		super.insertTime;
		[cal, dyn, mel, lmass, lharm, lcol].do(_.insertTime(index));
	}

	removeTime {|index|
		super.insertTime;
		[cal, dyn, mel, lmass, lharm, lcol].do(_.removeTime(index));
	}

	// makeView {|window|
	// 	view = TreeView(window);
	// 	view.columns = ["",""];
	// 	cal.makeView(view.addChild(["calibre",""]));
	// 	time.makeView(view.addChild(["temps",""]));
	// 	dyn.makeView(view.addChild(["amplitude",""]));
	// 	mel.makeView(view.addChild(["frequence",""]));
	// }

	// retourne la chaîne FLSC associée
	asString {
		^"(sonify % % % % % % %)".format(cal, time, dyn, mel, lmass, lharm, lcol);
	}
}
