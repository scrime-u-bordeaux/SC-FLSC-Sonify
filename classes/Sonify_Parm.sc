Sonify_Parm {
	var level, mods;

	*new {|lvl = -1, mods = #[]|
		^super.new.parmInit(lvl, mods);
	}

	parmInit {|lvl, mds|
		level = lvl;
		mods = mds;
	}

	asString {
		var lvlstr = if (level.isNumber)
		{ level.asString }
		{ "[%]".format(level.collect(_.asString).reduce('+')) };
		^"[%]".format(([lvlstr] ++ mods.collect(_.asString)).reduce('+'));
	}
}