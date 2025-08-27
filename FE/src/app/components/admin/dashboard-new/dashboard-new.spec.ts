import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardNew } from './dashboard-new';

describe('DashboardNew', () => {
  let component: DashboardNew;
  let fixture: ComponentFixture<DashboardNew>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardNew]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardNew);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
