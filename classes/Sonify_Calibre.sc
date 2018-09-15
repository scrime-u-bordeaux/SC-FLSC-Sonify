Sonify_Calibre : Sonify_Element {
	var num;

	*new {|n = 16, parent|
		^super.new(parent).calibreInit(n);
	}

	*default {|parent|
		^this.new(16, parent);
	}

	calibreInit {|n|
		num = n;
	}

	makeControls {
		controls = Slider(nil, Rect(0,0,256,32))
		.value_((num-8)/16)
		.action_ {|v| num = ((v.()*4).round.asInteger + 2) * 4};
		view.setView(1, controls);
	}

	asString {
		^num.asString;
	}
}