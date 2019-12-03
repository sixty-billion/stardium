package com.bb.stardium.player.web.controller;

import com.bb.stardium.player.domain.Player;
import com.bb.stardium.player.dto.PlayerRequestDto;
import com.bb.stardium.player.dto.PlayerResponseDto;
import com.bb.stardium.player.service.PlayerService;
import com.bb.stardium.player.service.exception.AuthenticationFailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private static final String IS_LOGIN_SUCCESS = "isLoginSuccess";
    private static final String REDIRECT_ROOT = "redirect:/";
    private static final String REDIRECT_LOGIN = "redirect:login";
    private static final String LOGIN_PLAYER = "LoginPlayer";

    private final PlayerService playerService;

    public PlayerController(final PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public String register(final PlayerRequestDto requestDto) {
        playerService.register(requestDto);
        return REDIRECT_LOGIN;
    }

    @PostMapping("/login")
    public String login(final PlayerRequestDto requestDto, final HttpSession session,
                        final RedirectAttributes redirectAttributes) {
        try {
            final Player player = playerService.login(requestDto);
            final PlayerResponseDto responseDto = new PlayerResponseDto(player);
            session.setAttribute(LOGIN_PLAYER, responseDto);
            redirectAttributes.addFlashAttribute(IS_LOGIN_SUCCESS, true);
            return REDIRECT_ROOT;
        } catch (final AuthenticationFailException exception) {
            redirectAttributes.addFlashAttribute(IS_LOGIN_SUCCESS, false);
            return REDIRECT_LOGIN;
        }
    }

    @GetMapping("/logout")
    public String logout(final HttpSession session) {
        session.invalidate();
        return REDIRECT_ROOT;
    }
}
