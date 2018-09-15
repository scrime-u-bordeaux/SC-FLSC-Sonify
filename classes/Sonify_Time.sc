Sonify_Time : Sonify_Element {
	var times;

	*new {|times = #[0.064, 1.024]|
		^super.new.timeInit(times);
	}

	*default {
		^this.new([0.064, 1.024]);
	}

	timeInit {|t|
		times = t;
		nbTimes = times.size;
	}

	*randGen {|time|
		^this.new({(rand(20.0)**2)*0.01} ! time);
	}

	setParent {|par|
		parent = par;
		parent.nbTimes = nbTimes;
	}

	insertTime {|index, item|
		times = times.insert(index, item);
		super.insertTime;
		parent.insertTime(index);
		this.makeControls;
	}

	removeTime {|index|
		times.removeAt(index);
		super.removeTime;
		parent.removeTime(index);
		this.makeControls;
	}

	asString {
		^"[%]".format(times.collect(_.asString).reduce('+'));
	}

	// makeControls {
	// 	if (controls.notNil) { controls.destroy };
	// 	controls = HLayoutView(nil);
	// 	times.size.do {|i|
	// 		Knob(controls).mode_(\horiz)
	// 		.value_(((times[i]*1000).log2 - 1)/12)
	// 		.action_ {|v| times[i] = (2**((v.()*12)+1)) * 0.001}
	// 	};
	// 	view.setView(1, controls);
	// }
}