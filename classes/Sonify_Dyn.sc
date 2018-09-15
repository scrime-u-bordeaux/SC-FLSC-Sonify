Sonify_Dyn : Sonify_Parm {
	*new {|lvl = #[-1,1,-1], mods = #[], parent|
		^super.new(lvl, mods, parent);
	}

	*default {|parent|
		^this.new([-1,1,-1], [], parent);
	}

	makeControls {
		if (controls.notNil) { controls.destroy };
		controls = HLayoutView(nil);
		StaticText(controls, Rect(0,0,16,16)).string_("0");
		(level.size-2).do {|i|
			var chk = CheckBox(controls).value_(level[i].notNil);
			var knb = Knob(controls).mode_(\horiz)
			.value_(((level[i+1]?0)+1)/2)
			.action_ {|v| if (chk.()) {level[i+1] = (v.()*2)-1}};
			chk.action_ {|v| if (v.()) {level[i+1] = (knb.()*2)-1} { level[i+1] = nil }};
		};
		StaticText(controls).string_("0");
		view.setView(1, controls);
	}
}