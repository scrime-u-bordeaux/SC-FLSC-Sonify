Sonify_Time {
	var times;

	*new {|times = #[0.064, 1.024]|
		^super.new.timeInit(times);
	}

	timeInit {|t|
		times = t;
	}

	asString {
		^"[%]".format(times.collect(_.asString).reduce('+'));
	}
}