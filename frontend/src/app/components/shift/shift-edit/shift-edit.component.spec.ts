import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShiftEditComponent } from './shift-edit.component';

describe('ShiftEditComponent', () => {
  let component: ShiftEditComponent;
  let fixture: ComponentFixture<ShiftEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShiftEditComponent]
    });
    fixture = TestBed.createComponent(ShiftEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
