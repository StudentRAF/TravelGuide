package rs.raf.student.mapper;

import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.dto.activity.ActivityGetDto;
import rs.raf.student.model.Activity;

public class ActivityMapper {

    public ActivityGetDto mapDto(Activity activity) {
        return new ActivityGetDto
            (
                activity.getId(),
                activity.getName()
            );
    }

    public Activity map(ActivityCreateDto createDto) {
        return map(new Activity(), createDto);
    }

    public Activity map(Activity activity, ActivityCreateDto createDto) {
        activity.setName(createDto.getName());

        return activity;
    }

}
