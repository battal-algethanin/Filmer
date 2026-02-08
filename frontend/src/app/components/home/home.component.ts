import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  title = 'Welcome to Filmer';
  features = [
    {
      icon: '🎬',
      title: 'Browse Movies',
      description: 'Explore thousands of movies from the IMDb dataset'
    },
    {
      icon: '🔍',
      title: 'Search & Filter',
      description: 'Find movies by genre, actor, or director'
    },
    {
      icon: '🛒',
      title: 'Rent Movies',
      description: 'Add movies to your cart and complete payment'
    },
    {
      icon: '📊',
      title: 'Track Orders',
      description: 'View your rental history and manage your account'
    }
  ];
}
