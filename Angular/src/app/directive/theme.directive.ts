// ./app/shared/hidden.directive.ts
import {Directive, ElementRef, Renderer2} from '@angular/core';
import {ThemeService} from "../services/theme.service";

@Directive({ selector: '[theme]' })
export class ThemeDirective {

  constructor(private el: ElementRef,
              private renderer: Renderer2,
              private themeService: ThemeService ) {}

  ngOnInit(){
    this.changeToTheme(this.themeService.getTheme());
    this.themeService.changeTheme.subscribe(
      (theme: string) => this.changeToTheme(theme)
    );
  }

  changeToTheme(theme: string) {
    if (theme == 'dark') {
      this.renderer.addClass(this.el.nativeElement, 'dark');
      this.renderer.removeClass(this.el.nativeElement, 'light');
    } else if (theme == 'light') {
      this.renderer.addClass(this.el.nativeElement, 'light');
      this.renderer.removeClass(this.el.nativeElement, 'dark');
    }
  }
}
