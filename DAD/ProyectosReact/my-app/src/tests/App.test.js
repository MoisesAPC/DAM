// test() y expect() son métodos de la librería Jest.
// render y screen son métodos de la librería React Testing Library
import { render, screen } from '@testing-library/react';
import App from '../App';  // Importamos el componente que queremos probar

/*
test('renders learn react link', () => {
  // Renderizamos el componente en un DOM virtual
  render(<App />);

  // Accedemos a la pantalla del componente renderizado con el método screen.
  // Y buscamos dentro de la pantalla el texto “learn react” usando el selector (o query) getByText
  const linkElement = screen.getByText(/learn react/i);

  // Le indicamos que esperamos (expect()) que en el documento esté el texto “learn react”
  expect(linkElement).toBeInTheDocument();
});
*/

describe('App componente', () => {
  // Renderizamos el componente en un DOM virtual
  render(<App />);

  // Test de h1
  it('Existe un h1', () => {
    expect(screen.getByRole('heading', { level: 1 })).toBeInTheDocument()
  });
  
  // Test de imagen
  it('Existe una imagen cuyo "alt" es "logo"', () => {
    expect(screen.getByRole('img', { name: 'logo' })).toBeInTheDocument()
  });
  
  // Test de imagen
  it('Existe una imagen cuyo "alt" es "logo"', () => {
    expect(screen.getByRole('img', { name: 'logo' })).toBeInTheDocument()
  });

  // Test de botón
  it('Existe un boton', () => {
    //el name del botón es el propio título que le ponemos al botón
    expect(screen.getByRole('button', { name: 'Botón de enviar de prueba' }))
  });
});
