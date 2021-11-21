package SerengetiLionProject.demo.controller;

import SerengetiLionProject.demo.domain.FinalSchedule;
import SerengetiLionProject.demo.domain.MeetGroup;
import SerengetiLionProject.demo.dto.MeetTeamFinalDateForm;
import SerengetiLionProject.demo.dto.MeetTeamNewGroupForm;
import SerengetiLionProject.demo.service.FinalScheduleService;
import SerengetiLionProject.demo.service.MeetGroupService;
import SerengetiLionProject.demo.service.TestMeetPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeamController {

    private MeetGroupService meetGroupService;
    private TestMeetPersonalService personalService;
    private FinalScheduleService finalScheduleService;

    @Autowired
    public TeamController(MeetGroupService onceMemberService, TestMeetPersonalService personalService, FinalScheduleService finalScheduleService) {
        this.meetGroupService = onceMemberService;
        this.personalService=personalService;
        this.finalScheduleService=finalScheduleService;
    }


    //현재 팀 (team_id가 할당되어있다 가정 -> '새로운 meet 만들기' 누르면 여기로 넘어옴
    @GetMapping(value="/fixed/{teamid}")
    public String createNewTeamMeet(Model model, @PathVariable("teamid") String teamid){
        Long team_id=Long.parseLong(teamid);
        model.addAttribute("team_id",team_id);
        return "createFixedMeetForm";
    }

    // "createFixedMeetForm" 에서 작성한 폼 (새로운 팀용 meet에 대한 정보): 여기로 넘어옴
    @PostMapping(value="/fixed/newmeet")
    public String createNewTeamMeetForm(MeetTeamNewGroupForm form){
        Long team_id=form.getTeam_id();
        MeetGroup meetGroup=new MeetGroup(form.getTitle(),form.getStart_date().toString(),form.getEnd_date().toString(),form.getStart_time(),form.getEnd_time(),"");
        meetGroup.setTeam_id(team_id);
        Long created_url=meetGroupService.SaveGroup(meetGroup);
        Long url_id=created_url;
        String url=url_id.toString();
        String title=meetGroup.getTitle();
        return "redirect:/fixed/"+title+"/"+url; //생성한 후 새롭게 만들어진 meet페이지로 넘겨줌!
    }

    //새롭게 생성된 현재 팀의 meet page -> 지금은 확정일정만,, 테스트하니까 확정일정용 페이지로 넘겨줌
    @GetMapping("/fixed/{title}/{urlid}")
    public String enterNewTeamMeetPage(Model model, @PathVariable("title") String title, @PathVariable("urlid") String urlid){
        Long url_id=Long.parseLong(urlid);
        MeetGroup group=meetGroupService.findOne(url_id);
        Long team_id=group.getTeam_id();
        model.addAttribute("teamid",team_id);
        model.addAttribute("title",title); //확정날짜 저장할 때 team_id랑 제목 저장해야하니까 jsp로 넘겨서 같이 저장 -> 받아오기
        return "saveFinalScheduleForm";
    }

    @PostMapping("/team/confirmMeetDate")
    public String finalDateForm(MeetTeamFinalDateForm form){
        //입력한 확정날짜 정보 받아와서 "FinalSchedule클래스 생성, db에 저장"
        FinalSchedule date=new FinalSchedule(form.getTeam_id(),form.getTitle(),form.getFinal_date(),form.getStart_hour(),form.getStart_min()
        ,form.getEnd_hour(),form.getEnd_min());
        finalScheduleService.saveFinalSchedule(date);
        return "redirect:/";
    }
}