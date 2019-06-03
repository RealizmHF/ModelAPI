package com.ticxo.modelapi.api.animation.preset;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

import com.ticxo.modelapi.api.animation.Animation;
import com.ticxo.modelapi.api.modeling.Offset;
import com.ticxo.modelapi.api.modeling.Part;
import com.ticxo.modelapi.math.Quaternion;

public class SequenceAnimation implements Animation {

	private Sequence animation;
	private String partType = "body";
	private int frame = 0;
	
	public SequenceAnimation(Sequence animation) {
		this.animation = animation;
	}
	
	public SequenceAnimation(Sequence animation, String partType) {
		this.animation = animation;
		this.partType = partType;
	}
	
	@Override
	public void entityParentConnection(Entity parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body) {
		
		Offset pos = part.getLocationOffset(),
				fpos = animation.getOffset(frame);
		
		Location l = parent.getLocation();
		l.setYaw(0);
		l.setPitch(0);
		l.add(pos.getX() + fpos.getX(), pos.getY() - 0.725 + fpos.getY(), pos.getZ() + fpos.getZ());
		
		target.teleport(l);
		EulerAngle angle = Quaternion.combine(part.getRotationOffset(), animation.getRotation(frame));
		if(partType.toLowerCase().equals("head"))
			angle = Quaternion.combine(angle, head);
		else
			angle = Quaternion.combine(angle, body);
		target.setHeadPose(angle);
		
		frame++;
		frame %= animation.getLength();
	}

	@Override
	public void partParentConnection(ArmorStand parent, ArmorStand target, Part part, EulerAngle head, EulerAngle body) {

		Offset pos = part.getLocationOffset(),
				fpos = animation.getOffset(frame),
				apos = new Offset(pos.getX() + fpos.getX(), pos.getY() + fpos.getY(), pos.getZ() + fpos.getZ());
		
		target.teleport(apos.getRelativeLocation(parent.getLocation(), parent.getHeadPose()));
		EulerAngle angle = Quaternion.combine(part.getRotationOffset(), animation.getRotation(frame));
		if(partType.toLowerCase().equals("head"))
			angle = Quaternion.combine(angle, head);
		else
			angle = Quaternion.combine(angle, parent.getHeadPose());
		target.setHeadPose(angle);
		
		frame++;
		frame %= animation.getLength();
		
	}

	@Override
	public Animation createAnimation() {
		return new SequenceAnimation(animation.createSequence(), partType);
	}

}