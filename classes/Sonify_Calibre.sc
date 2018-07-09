Sonify_Calibre {
	var num;

	*new {|n = 16|
		^super.new.calibreInit(n);
	}

	calibreInit {|n|
		num = n;
	}

	asString {
		^num.asString;
	}
}